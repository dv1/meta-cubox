# this file contains facilities to unpack cubox-packages.tar.gz
# this archive is the original source package from Marvell, and contains other archives
# (tarballs and deb packages)
#
# recipes which need sources from inside this archive need to include this file first,
# then define the following variables:
# * SUBPKG_FILENAME : Filename of subpackage to unpack, including the relative path inside the
#   cubox-packages.tar.gz archive. Example: SUBPKG_FILENAME="gst-plugins-marvell/gst-plugins-marvell0.10_0.2.0-0ubuntu1~ppa14.tar.gz"
# * SUBPKG_TYPE : Type of subpackage to unpack. Valid values are "deb" (for debian packages)
#   and "tar" (for tarballs, including any compression variant; that is, "tar" is valid for
#   .tar, .tar.bz2, .tar.gz, .tar.xz ...)
# * SUBPKG_SUBDIR : Subdirectory to create in ${WORKDIR}. The package will be unpacked there.
#   If SUBPKG_SUBDIR is not set, the package will be unpacked in ${WORKDIR}.
# SUBPKG_SUBDIR is optional, the others are required.
# After defining these variables, the recipe needs to call the unpack_subpackage function. Typically, it is
# added to do_unpack as a postfunc:   do_unpack[postfuncs] += "unpack_subpackage"
# This way, the big archive is fetched and unpacked if necessary, and afterwards, the subpackage is unpacked.
# It is not automatically added as a postfunc here, because in some cases, recipes might want to do something more elaborate
# than just add it as a postfunc (for example, unpack multiple subpackages). In such cases, they typically define their own
# unpack postfunc, and call unpack_subpackage inside.


# TODO: mirrors for this file would be nice..
SRC_URI = "http://download.solid-run.com/pub/solidrun/cubox/packages/cubox-packages-source/cubox-packages.tar.gz;name=cubox-packages"
SRC_URI[cubox-packages.md5sum] = "6387e85fbe85747bc8f646e87a16c407"
SRC_URI[cubox-packages.sha256sum] = "1b530afaa666652b12a97aef562d13438de833453bd2b905ac3c63d1bcc7263d"

PACKAGES_DIR = "${WORKDIR}/packages"

DEPENDS += "dpkg-native"


# The function that actually unpacks a specific subpackage
unpack_subpackage() {
	if [ -n ${SUBPKG_SUBDIR} ]
	then
		DESTDIR="${WORKDIR}/${SUBPKG_SUBDIR}"
	else
		DESTDIR="${WORKDIR}"
	fi
	bbnote "Unpacking subpackage ${SUBPKG_FILENAME} of type ${SUBPKG_TYPE} in ${DESTDIR}"
	case ${SUBPKG_TYPE} in
		"tar")
			tar xf "${PACKAGES_DIR}/${SUBPKG_FILENAME}" -C "${DESTDIR}"
			;;
		"deb")
			dpkg-deb -x "${PACKAGES_DIR}/${SUBPKG_FILENAME}" "${DESTDIR}"
			;;
		*)
			bbfatal "Unsupported subpackage type ${SUBPKG_TYPE}"
			;;
	esac
}

