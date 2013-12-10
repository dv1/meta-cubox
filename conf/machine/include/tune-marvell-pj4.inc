DEFAULTTUNE ?= "marvellpj4"

require conf/machine/include/arm/arch-armv7a.inc

TUNEVALID[marvellpj4] = "Enable Marvell PJ4 specific processor optimizations"
TUNE_CCARGS += "${@bb.utils.contains("TUNE_FEATURES", "marvellpj4", "-mfpu=vfpv3-d16 -mtune=marvell-pj4", "", d)}"

AVAILTUNES += "marvellpj4"
ARMPKGARCH_tune-marvellpj4 ?= "marvellpj4"
TUNE_FEATURES_tune-marvellpj4 ?= "${TUNE_FEATURES_tune-armv7a} marvellpj4"
PACKAGE_EXTRA_ARCHS_tune-marvellpj4 = "${PACKAGE_EXTRA_ARCHS_tune-armv7a} marvellpj4 marvellpj4-vfp"

AVAILTUNES += "marvellpj4hf"
ARMPKGARCH_tune-marvellpj4hf ?= "marvellpj4"
TUNE_FEATURES_tune-marvellpj4hf ?= "${TUNE_FEATURES_tune-armv7ahf} marvellpj4"
PACKAGE_EXTRA_ARCHS_tune-marvellpj4hf = "${PACKAGE_EXTRA_ARCHS_tune-armv7ahf} marvellpj4hf-vfp"
