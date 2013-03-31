SUMMARY = "Closed-source codecs and libraries for Marvell's vMeta"
SECTION = "multimedia"
LICENSE = "CLOSED"

SUBPKG_TYPE="tar"
SUBPKG_FILENAME="marvell-ipp/marvell-ipp_0.2.1-0ubuntu1~ppa10.tar.gz"

require ../../common/cubox-packages.inc

S = "${WORKDIR}"

LIBMISCGEN_HARDFP_URI = "http://download.solid-run.com/pub/solidrun/cubox/packages/marvell-vmeta/libmiscgen-hardfp.tar.xz;name=libmiscgen"
VMETA_HARDFP_URI = "http://download.solid-run.com/pub/solidrun/cubox/packages/marvell-vmeta/vmeta-hardfp.tar.xz;name=vmetacodec"


python () {
	# Append extra packages with hardfp binaries if callconvention-hard is present in the TUNE_FEATURES
	if base_contains('TUNE_FEATURES', 'callconvention-hard', True, False, d):
		d.appendVar('SRC_URI', ' ' + d.getVar('LIBMISCGEN_HARDFP_URI', True))
		d.appendVar('SRC_URI[libmiscgen.md5sum]', ' c7f22deed01b803d7a0d9cef9d996ee1')
		d.appendVar('SRC_URI[libmiscgen.sha256sum]', ' 590580f3e83612ce2f23d0770281951f3d84bf0f50fb2138d1ed15e0c79a1699')
		d.appendVar('SRC_URI', ' ' + d.getVar('VMETA_HARDFP_URI', True))
		d.appendVar('SRC_URI[vmetacodec.md5sum]', ' 30eb47cd675c81c4e6ee0858d1fbc3a7')
		d.appendVar('SRC_URI[vmetacodec.sha256sum]', ' f3d2c37b587e638670127ec227056878a0717ace8a646d7eee7c4f67e5b9506e')
}

do_unpack[postfuncs] += "unpack_subpackage"

do_install() {
	install -d ${D}${includedir}
	install -d ${D}${libdir}
	install -m 0644 ${S}/marvell-ipp/include/*.h ${D}${includedir}

	# not installing .so shared objects as .so.1 , since other closed source modules
	# expect endings as they are (existing .so.1 files do get symlinked to .so)
	if [ "x${@base_contains('TUNE_FEATURES', 'callconvention-hard', 'yes', '', d)}" == "xyes" ]
	then
		# Install hardfp binaries from the vmeta-hardfp and libmiscgen tarballs

		# static libraries
		install -m 0644 ${S}/vmeta-hardfp/*.a ${D}${libdir}

		# shared objects
		install -m 0755 ${S}/libmiscgen-hardfp/libmiscgen.so ${D}${libdir}
		install -m 0755 ${S}/vmeta-hardfp/libcodecvmetadec.so.1 ${D}${libdir}
		install -m 0755 ${S}/vmeta-hardfp/libvmetahal.so ${D}${libdir}
	else
		# Install softfp binaries from the marvell-ipp tarball

		# static libraries
		install -m 0644 ${S}/marvell-ipp/lib/*.a ${D}${libdir}

		# remove existing symlinks (they interfere with the symlink generation process below)
		rm -f ${S}/marvell-ipp/bin/libcodecvmetadec.so
		rm -f ${S}/marvell-ipp/bin/libcodecvmetaenc.so

		# install all softfp libraries from marvell-ipp
		install -m 0755 ${S}/marvell-ipp/bin/*.so* ${D}${libdir}
	fi

	# generate symlinks for all .so.1 files
	cd ${D}${libdir}
	for srclib in *.so.1
	do
		destlib=$(echo ${srclib} | sed -e 's/\.so\.1$/.so/')
		ln -s ${srclib} ${destlib}
	done
}

FILES_${PN} = " \
	${libdir}/*.so* \
"

FILES_${PN}-staticdev = " \
	${libdir}/*.a \
"

FILES_${PN}-dev = " \
	${includedir}/*.h \
"

# Closed source binaries are already stripped
INHIBIT_PACKAGE_STRIP = "1"

# This is necessary for the closed-source HAL and codec libraries
INSANE_SKIP_${PN} += "ldflags dev-so"

