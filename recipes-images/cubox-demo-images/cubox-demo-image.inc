IMAGE_FEATURES += "package-management ssh-server-dropbear x11-base"

LICENSE = "MIT"

inherit core-image

COMMERCIAL_FLAGGED_PACKAGES = " \
	gst-plugins-ugly-meta \
        gst-ffmpeg \
	gst-fluendo-mpegdemux \
	gst-fluendo-mp3 \
"

IMAGE_INSTALL += " \
        gstreamer \
        gst-plugins-base-meta \
        gst-plugins-good-meta \
        gst-plugins-bad-meta \
	${@base_contains('LICENSE_FLAGS_WHITELIST', 'commercial', '${COMMERCIAL_FLAGGED_PACKAGES}', '', d)} \
	marvell-libgfx \
	marvell-libbmm \
	marvell-libvmeta \
        gst-plugins-marvell \
        gst-plugins-bmmxv \
"

IMAGE_LINGUAS = " "


copy_uimage_and_bootscript() {
	mkdir -p "${IMAGE_ROOTFS}/boot"
	cp "${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}-${MACHINE}.bin" "${IMAGE_ROOTFS}/boot/${KERNEL_IMAGETYPE}"
	mkimage -A arm -O linux -a 0 -e 0 -T script -C none -n "CuBox boot script for SD card" -d "${FILE_DIRNAME}/../../conf/boot/boot.script" "${IMAGE_ROOTFS}/boot/boot.scr"
}


ROOTFS_POSTPROCESS_COMMAND += "copy_uimage_and_bootscript ; "

