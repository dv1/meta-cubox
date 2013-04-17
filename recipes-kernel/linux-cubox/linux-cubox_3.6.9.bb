require linux.inc

DESCRIPTION = "Linux kernel for the CuBox device"

SRC_URI = "git://github.com/rabeeh/linux.git;protocol=git;branch=master"
SRCREV = "24b03f88d3eaee1aabfdfcae8387e0576c67d1c2"
S = "${WORKDIR}/git"

LINUX_VERSION ?= "3.6.9"

COMPATIBLE_MACHINE = "cubox"

KERNEL_DEFCONFIG = "cubox_defconfig"

PARALLEL_MAKEINST = ""

PR = "r3"
PV = "${LINUX_VERSION}+git${SRCPV}"

do_configure_prepend() {
	if [ ! -f ${WORKDIR}/defconfig ]
	then
		bbnote "No ${WORKDIR}/defconfig file found - using default cubox_defconfig"
		install -m 0644 ${S}/arch/${ARCH}/configs/${KERNEL_DEFCONFIG} ${WORKDIR}/defconfig || die "No default configuration for ${MACHINE} / ${KERNEL_DEFCONFIG} available."
	fi
}

