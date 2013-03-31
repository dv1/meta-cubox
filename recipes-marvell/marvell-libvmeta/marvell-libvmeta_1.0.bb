DESCRIPTION = "Marvell's vMeta library"
SECTION = "multimedia"

LICENSE = "GPLv2+ & LGPLv2+"
LIC_FILES_CHKSUM = " \
	file://uio_vmeta.h;beginline=2;endline=6;md5=3379670fad249bec4988652c6749248f \
	file://vmeta_lib.c;beginline=2;endline=20;md5=5bac88bfe8f2ee3158dc943fa9e38a48 \
	"

SUBPKG_TYPE = "tar"
SUBPKG_FILENAME = "libvmeta/libvmeta_1.0ubuntu2.tar.gz"

DEPENDS += "marvell-libbmm"

require ../../common/cubox-packages.inc

S = "${WORKDIR}/libvmeta"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://makefile_fixes.patch"

do_unpack[postfuncs] += "unpack_subpackage"

do_compile() {
	oe_runmake
}

do_install() {
	install -d ${D}${includedir}
	install -d ${D}${libdir}
	install -m 0644 ${S}/*.h ${D}${includedir}
	install -m 0644 ${S}/*.a ${D}${libdir}
	# not installing the shared object as .so.1,
	# since closed-source marvell codecs expect a .so to be present, not .so.1
	install -m 0755 ${S}/libvmeta.so ${D}${libdir}
}


PACKAGES = "${PN} ${PN}-dev ${PN}-staticdev ${PN}-dbg"

FILES_${PN} = " \
	${libdir}/*.so \
"

FILES_${PN}-staticdev = " \
	${libdir}/*.a \
"

FILES_${PN}-dev = " \
	${includedir}/*.h \
"

FILES_${PN}-dbg = " \
	${prefix}/src \
	${libdir}/.debug/* \
"

