SUMMARY = "Marvell's buffer management module library"
SECTION = "libs"
LICENSE = "GPLv2+"

SRC_URI = "git://dev.laptop.org/users/dsd/libphycontmem"
SRCREV = "826f492a0dab4a65e767296cec24d1c9e0205020"

LIC_FILES_CHKSUM = "file://libphycontmem.spec;md5=78b17b3eb61a557b3a95bdc5a28cec00"


S = "${WORKDIR}/git"

do_compile() {
	oe_runmake libphycontmem.so
}

do_install() {
	install -d ${D}${includedir}
	install -d ${D}${libdir}
	install -m 0644 ${S}/*.h ${D}${includedir}
	install -m 0755 ${S}/*.so ${D}${libdir}
}

PACKAGES = "${PN} ${PN}-dev ${PN}-dbg"

FILES_${PN} = " \
	${bindir}/* \
	${libdir}/*.so \
"

FILES_${PN}-dev = " \
	${includedir}/*.h \
"

FILES_${PN}-dbg = " \
	${prefix}/src \
	${libdir}/.debug/* \
"

INSANE_SKIP_${PN} += "dev-so"

