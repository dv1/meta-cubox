DESCRIPTION = "Marvell's libgfx library for EGL, OpenGL ES 1 & 2, OpenVG support"
SECTION = "graphics"
LICENSE = "CLOSED"

LIBSPKG_hardfp = "http://download.solid-run.com/pub/solidrun/cubox/packages/marvell-opengl/marvell-opengl-hardfp-light.zip;name=hardfplibs"
LIBGFX_DIR_hardfp = "gc3184-1-mgcc462hd-r"
LIBSPKG_softfp = "http://download.solid-run.com/pub/solidrun/cubox/packages/marvell-opengl/marvell-opengl-softfp-light.zip;name=softfplibs"
LIBGFX_DIR_softfp = "gc3184-1-mgcc462sf-r"

SRC_URI = "http://download.solid-run.com/pub/solidrun/cubox/packages/marvell-libgfx/marvell-libgfx-headers-20120713.tar.bz2;name=headers"
SRC_URI[headers.md5sum] = "a381d9d6d0c5beda781a23dea2c3e0ea"
SRC_URI[headers.sha256sum] = "81a4214361782d5a17932fb8781b92d2812be582033bb878a2ef08515ac9c852"

SRC_URI[hardfplibs.md5sum] = "6835a82fdc45c2ae23465e751db7850d"
SRC_URI[hardfplibs.sha256sum] = "6b7c6c87d753ea61dbe891301c1af25e9ea8c9c5959f78a23b697225879a5608"
SRC_URI[softfplibs.md5sum] = "33d914b7c29b68b979330532a68bdad9"
SRC_URI[softfplibs.sha256sum] = "9131eb077007c7dc522ba3b2be0f3530e2152f4feca0596831121ca79c05df0e"

python () {
	callconvention = base_contains('TUNE_FEATURES', 'callconvention-hard', 'hardfp', 'softfp', d)
	d.appendVar('SRC_URI', " " + d.getVar('LIBSPKG_%s' % callconvention, True))
	d.setVar('LIBGFX_DIR', d.getVar('LIBGFX_DIR_%s' % callconvention, True))
}


PR="r1"

COMPATIBLE_MACHINE = "(cubox)"

S = "${WORKDIR}"

PROVIDES += "virtual/egl virtual/libgles1 virtual/libgles2"

do_patch() {
}

do_configure() {
}

do_compile() {
}

do_install() {
	# not installing .so shared objects as .so.1 , since other closed source modules
	# expect endings as they are

	install -d "${D}${includedir}"
	install -d "${D}${includedir}/EGL"
	install -d "${D}${includedir}/GLES"
	install -d "${D}${includedir}/GLES2"
	install -d "${D}${includedir}/HAL"
	install -d "${D}${includedir}/KHR"
	install -d "${D}${includedir}/VG"
	install -d "${D}${libdir}"

	install -m 0644 ${S}/include/EGL/* ${D}${includedir}/EGL
	install -m 0644 ${S}/include/GLES/* ${D}${includedir}/GLES
	install -m 0644 ${S}/include/GLES2/* ${D}${includedir}/GLES2
	install -m 0644 ${S}/include/HAL/* ${D}${includedir}/HAL
	install -m 0644 ${S}/include/KHR/* ${D}${includedir}/KHR
	install -m 0644 ${S}/include/VG/* ${D}${includedir}/VG
	install -m 0755 ${S}/${LIBGFX_DIR}/${lib}/*.so "${D}${libdir}"

	# Creating symlink, since many packages assume the existence of a "libGLESv2.so"
	cd "${D}${libdir}"
	ln -s "libGLESv2x.so" "libGLESv2.so"
}

PACKAGES = "${PN} ${PN}-dev ${PN}-dbg"

FILES_${PN} = " \
	${libdir}/*.so* \
	"

FILES_${PN}-dev = " \
	${includedir}/*.h \
	${includedir}/EGL/*.h \
	${includedir}/EGL/*.h \
	${includedir}/GLES/*.h \
	${includedir}/GLES2/*.h \
	${includedir}/HAL/*.h \
	${includedir}/KHR/*.h \
	${includedir}/VG/*.h \
	"

FILES_${PN}-dbg = " \
	${libdir}/.debug \
	"

# This is necessary for the closed-source binaries
INSANE_SKIP_${PN} += "ldflags dev-so"

