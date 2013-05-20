require recipes-graphics/xorg-driver/xorg-driver-video.inc

SUMMARY = "X.Org X server -- Marvell Dove driver"
DESCRIPTION = "X.Org driver for the Marvell Dove (Armada 500 series) platform"
SECTION = "graphics"
LICENSE = "MIT"

SRC_URI = "git://dev.laptop.org/projects/xf86-video-dove"
SRCREV = "b08f0c2daa455bcdfd4d964c12cc3107a6a05a9b"

LIC_FILES_CHKSUM = "file://COPYING;md5=e4ef7bc55bbdff56a9bf77007659c9ac"

DEPENDS += "marvell-libgfx"

PV = "0.9.3+git${SRCPV}"
PR = "r1"

S = "${WORKDIR}/git"

inherit autotools

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# of all these CFLAGS, -DMRVL_PLATFORM_INFO=1 is probably the most important
# since the value "1" here means "Dove platform"
CFLAGS += " \
	-DMRVL_SUPPORT_RANDR=1 \
	-DMRVL_SUPPORT_EXA=1 \
	-DDUMP_RAW_VIDEO=0 \
	-DMRVL_USE_OFFSCREEN_HEAP=0 \
	-DMRVL_EXA_MODE=2 \
	-DMRVL_EXA_ENABLE_UP_DOWNLOAD=2 \
	-DMRVL_EXA_FORCE_HW_LOAD=0 \
	-DMRVL_EXA_ALLOC_PIXMAP_FROM_SYSTEM=0 \
	-DMRVL_EXA_PERF_PROFILING=0 \
	-DMRVL_EXA_TRACE_FALLBACK=0 \
	-DMRVL_EXA_XBGR_SUPPORT=1 \
	-DMRVL_XV_SUPPORT_RGB_FORMAT=1 \
	-DMRVL_XV_TEX_VIDEO=1 \
	-DMRVL_XV_OVERLAY_VIDEO=1 \
	-DMRVL_XV_DEFERRED_STALL_GPU=1 \
	-DMRVL_XV_USE_FAKE_FENCE_STALL=1 \
	-DMRVL_RANDR_EDID_MODES=1 \
	-DMRVL_CRTC_SUPPORT_ROTATION=1 \
	-DMRVL_PLATFORM_INFO=1 \
	-I${STAGING_INCDIR}/HAL \
	-I${S} \
"

SRC_URI += " \
	file://0001-autoconf-related-fixes.patch \
	file://0002-Added-missing-enum.patch \
	file://mibstore.h \
"

do_compile[prefuncs] += "copy_mibstore_header"


copy_mibstore_header() {
	cp "${WORKDIR}/mibstore.h" "${S}/mibstore.h"
}

