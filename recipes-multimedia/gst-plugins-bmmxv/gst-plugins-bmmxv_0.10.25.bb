SUMMARY = "Patched GStreamer xvimagesink using Marvell's Buffer Management Module (BMM)"
DESCRIPTION = "This xvimagesink is based on the one from gst-plugins-base; element is called bmmxvimagesink"
SECTION = "multimedia"

LICENSE = "GPLv2+ & LGPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=0636e73ff0215e8d672dc4c32c317bb3 \
                    file://common/coverage/coverage-report.pl;beginline=2;endline=17;md5=622921ffad8cb18ab906c56052788a3f \
                    file://COPYING.LIB;md5=55ca817ccb7d5b5b66355690e9abc605 \
                   "

SUBPKG_TYPE="tar"
SUBPKG_FILENAME="gst-plugins-bmmxv/gst-plugins-bmmxv0.10_0.10.25-0ubuntu1~ppa9.tar.gz"

require ../../common/cubox-packages.inc

DEPENDS += "marvell-libbmm gstreamer gst-plugins-base liboil virtual/libx11 libxv"

S = "${WORKDIR}/gst-plugins-bmmxv"

inherit autotools pkgconfig gettext

do_unpack[postfuncs] += "unpack_subpackage"

FILES_${PN} = "${libdir}/gstreamer-0.10/*.so ${datadir}"
FILES_${PN}-dbg += "${libdir}/gstreamer-0.10/.debug"
FILES_${PN}-dev += "${libdir}/gstreamer-0.10/*.la"
FILES_${PN}-staticdev += "${libdir}/gstreamer-0.10/*.a"

