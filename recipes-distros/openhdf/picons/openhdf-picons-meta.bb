DESCRIPTION = "openhdf meta package for enigma2 picon sets"
SECTION = "base"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

ALLOW_EMPTY_${PN} = "1"
PV = "1.0"
PR = "r1"

DEPENDS = " \
	enigma2-plugin-skins-nobile \
"
