require linux.inc

DESCRIPTION = "Linux kernel for the CuBox device"

SRC_URI = "git://github.com/rabeeh/linux.git;protocol=git;branch=master"
SRCREV = "496e29045d7f18a438c1cf05a9afeb47d51b2e6f"
S = "${WORKDIR}/git"

LINUX_VERSION ?= "3.6.9"

COMPATIBLE_MACHINE = "cubox"

KERNEL_DEFCONFIG = "cubox_defconfig"

PARALLEL_MAKEINST = ""

PR = "r4"
PV = "${LINUX_VERSION}+git${SRCPV}"

do_configure_prepend() {
	if [ ! -f ${WORKDIR}/defconfig ]
	then
		bbnote "No ${WORKDIR}/defconfig file found - using default cubox_defconfig"
		install -m 0644 ${S}/arch/${ARCH}/configs/${KERNEL_DEFCONFIG} ${WORKDIR}/defconfig || die "No default configuration for ${MACHINE} / ${KERNEL_DEFCONFIG} available."
	fi
}

