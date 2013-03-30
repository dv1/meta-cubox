SUMMARY = "Marvell's buffer management module library"
SECTION = "libs"
LICENSE = "LGPL-2.1"

LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=2d5025d4aa3495befef8f17206a5b0a1"

SUBPKG_TYPE = "tar"
SUBPKG_FILENAME = "bmm-lib/marvell-libbmm_0.2.0-0ubuntu1~ppa8.tar.gz"

require ../../common/cubox-packages.inc

S = "${WORKDIR}/lib"

EXTRA_OEMAKE = "-f Makefile_general"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://makefile_fixes.patch"

do_unpack[postfuncs] += "unpack_subpackage"

do_compile() {
	oe_runmake bmm_test libbmm.a
}

do_install() {
	install -d ${D}${includedir}
	install -d ${D}${libdir}
	install -d ${D}${bindir}
	install -m 0644 ${S}/*.h ${D}${includedir}
	install -m 0644 ${S}/*.a ${D}${libdir}
	install -m 0755 ${S}/*.so.0u.1 ${D}${libdir}
	# preserve *.so symlinks (install doesn't)
	# TODO: is this the right way to do it?
	cp -pP ${S}/*.so.0u ${D}${libdir}
	cp -pP ${S}/*.so ${D}${libdir}
	install -m 0755 ${S}/bmm_test ${D}${bindir}
}

PACKAGES = "${PN} ${PN}-dev ${PN}-staticdev ${PN}-dbg"

FILES_${PN} = " \
	${bindir}/* \
	${libdir}/*.so.0u.1 \
"

FILES_${PN}-staticdev = " \
	${libdir}/*.a \
"

FILES_${PN}-dev = " \
	${libdir}/*.so \
	${libdir}/*.so.0u \
	${includedir}/*.h \
"

FILES_${PN}-dbg = " \
	${prefix}/src \
	${libdir}/.debug/* \
	${bindir}/.debug/* \
"

