DESCRIPTION = "Merge machine and distro options to create a oe-allinace enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
PV = "1.0"
PR = "r8"

inherit task

DEPENDS = "enigma2-plugin-drivers-usbserial"

RRECOMMENDS = "\
	bootlogos-enigma2-meta \
	dvb-usb-drivers-meta \
	channelsettings-enigma2-meta \
	picons-enigma2-meta \
	task-openplugins \
	\
	enigma2-skins \
	enigma2-plugins \
	enigma2-plugin-drivers-ntfs-3g \
	enigma2-plugin-drivers-usbserial \
	enigma2-plugin-extensions-ambx \
	enigma2-plugin-extensions-tuxcom \
	enigma2-plugin-extensions-tuxterm \
	enigma2-plugin-extensions-webinterface-old \
	enigma2-plugin-security-firewall \
	enigma2-plugin-skins-pli-hd \
	enigma2-plugin-extensions-openairplay \
	enigma2-plugin-extensions-et-livestream \
	enigma2-plugin-extensions-mediatomb \
	${@base_contains("MACHINE_FEATURES", "fullgraphiclcd", "lcdpicons-enigma2-meta" , "", d)} \
	\
	autossh \
	avahi-ui \
	ctorrent \
	cups \
	djmount \
	dosfstools \
	dvbsnoop \
	dvdfs \
	gdb \
	hddtemp \
	hdparm \
	htop \
	inadyn-mt \
	iperf \
	joe \
	mc \
	minidlna \
	mpd \
	mtd-utils \
	nano \
	net-snmp \
	ntfs-3g \
	ntp \
	openresolv \
	openssh \
	openvpn \
	parted \
	procps \
	rsync \
	sabnzbd \
	samba \
	smartmontools \
	sshpass \
	strace \
	transmission \
	tcpdump \
	ushare \
	vim \
	wakelan \
	zeroconf \
	pyload \
	"
