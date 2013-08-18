SUMMARY = "Marvell's buffer management module library"
SECTION = "libs"
LICENSE = "GPLv2+"

SRC_URI = "git://dev.laptop.org/users/dsd/gst-plugins-vmetaxv"
SRCREV = "07a12cd92bc6ec44d13fc4451f360c4f01154939"

LIC_FILES_CHKSUM = "file://gstreamer-plugins-vmetaxv.spec;md5=59370420cf6ae317b77111d168384d2e"

DEPENDS += "marvell-libphycontmem gstreamer gst-plugins-base liboil virtual/libx11 libxv"


S = "${WORKDIR}/git"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://vmetaxv_makefile_fixes.patch"

do_compile() {
	oe_runmake
}

do_install() {
	install -d ${D}${libdir}/gstreamer-0.10
	install -m 0755 ${S}/*.so ${D}${libdir}/gstreamer-0.10
}

PACKAGES = "${PN} ${PN}-dbg"

FILES_${PN} = " \
	${libdir}/gstreamer-0.10/*.so \
"

FILES_${PN}-dbg = " \
	${prefix}/src \
	${libdir}/gstreamer-0.10/.debug/* \
"
INSANE_SKIP_${PN} += "dev-so"

