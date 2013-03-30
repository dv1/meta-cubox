require linux.inc

DESCRIPTION = "Linux kernel for the CuBox device"

SRC_URI = "git://github.com/rabeeh/linux.git;protocol=git;branch=master"
SRCREV="1be2c7686ab85ab11f92e90843cd8e0ea2cc93ac"
S = "${WORKDIR}/git"

LINUX_VERSION ?= "3.6.9"

COMPATIBLE_MACHINE = "cubox"

KERNEL_DEFCONFIG = "cubox_defconfig"

PARALLEL_MAKEINST = ""

PR = "r1"
PV = "${LINUX_VERSION}+git${SRCPV}"

do_configure_prepend() {
	install -m 0644 ${S}/arch/${ARCH}/configs/${KERNEL_DEFCONFIG} ${WORKDIR}/defconfig || die "No default configuration for ${MACHINE} / ${KERNEL_DEFCONFIG} available."
}

