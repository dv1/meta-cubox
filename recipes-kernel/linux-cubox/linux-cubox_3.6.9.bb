require linux.inc

DESCRIPTION = "Linux kernel for the CuBox device"

SRC_URI = "git://github.com/rabeeh/linux.git;protocol=git;branch=master"
SRCREV = "b9fc5ff6f6b56a20c0d247679b46b4d8fb14b801"
S = "${WORKDIR}/git"

LINUX_VERSION ?= "3.6.9"

COMPATIBLE_MACHINE = "cubox"

KERNEL_DEFCONFIG = "cubox_defconfig"

PARALLEL_MAKEINST = ""

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += " \
	file://uio-vmeta-event-count-fix.patch \
"

PR = "r2"
PV = "${LINUX_VERSION}+git${SRCPV}"

do_configure_prepend() {
	if [ ! -f ${WORKDIR}/defconfig ]
	then
		bbnote "No ${WORKDIR}/defconfig file found - using default cubox_defconfig"
		install -m 0644 ${S}/arch/${ARCH}/configs/${KERNEL_DEFCONFIG} ${WORKDIR}/defconfig || die "No default configuration for ${MACHINE} / ${KERNEL_DEFCONFIG} available."
	fi
}

