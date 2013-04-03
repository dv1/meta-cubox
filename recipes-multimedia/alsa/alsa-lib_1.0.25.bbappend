PRINC := "${@int(PRINC) + 1}"

KIRKWOOD_SPDIF_CONF = "Kirkwood_SPDIF.conf"

FILESEXTRAPATHS_prepend := "${THISDIR}/alsa-lib:"
SRC_URI += "file://${KIRKWOOD_SPDIF_CONF}"

install_kirkwood_spdif_conf () {
	install -d "${datadir}/alsa/cards"
	install -m 0644 "${S}/../${KIRKWOOD_SPDIF_CONF}" "${D}${datadir}/alsa/cards"
}

do_install[postfuncs] += "install_kirkwood_spdif_conf"

