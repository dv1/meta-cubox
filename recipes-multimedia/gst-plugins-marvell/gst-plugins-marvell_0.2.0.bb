SUMMARY = "GStreamer plugins for Marvell's vMeta and IPP codecs"
SECTION = "multimedia"

SUBPKG_TYPE="tar"
SUBPKG_FILENAME="gst-plugins-marvell/gst-plugins-marvell0.10_0.2.0-0ubuntu1~ppa14.tar.gz"

require ../../common/cubox-packages.inc

DEPENDS += "marvell-libvmeta marvell-codecs gstreamer gst-plugins-base"

LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = " \
		file://src/vmeta/gstvmetadec.h;beginline=5;endline=18;md5=fc1da099fd60d3a6c487ee5e70379d08 \
		"

S = "${WORKDIR}/gst-plugins-marvell"

inherit autotools pkgconfig

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# Add patch to disable IPP elements if hardfp is used (IPP codecs are softfp only)
SRC_URI += "${@base_contains('TUNE_FEATURES', 'callconvention-hard', 'file://0001-Disabled-IPP-elements-since-marvell-ipp-is-softfp.patch', '', d)}"


do_unpack[postfuncs] += "unpack_subpackage"

FILES_${PN} = "${libdir}/gstreamer-0.10/*.so ${datadir}"
FILES_${PN}-dbg += "${libdir}/gstreamer-0.10/.debug"
FILES_${PN}-dev += "${libdir}/gstreamer-0.10/*.la"
FILES_${PN}-staticdev += "${libdir}/gstreamer-0.10/*.a"

