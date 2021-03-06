DESCRIPTION = "OpenMips Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "openmips team"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "r3${DATETIME}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

IMAGE_INSTALL = " \
	oe-alliance-enigma2 \
	openmips-version-info \
	openmips-enigma2 \
	openmips-bootlogo \
	${ENIGMA2_PLUGINS} \
	avahi-daemon \
	dropbear \
	early-configure \
	e2fsprogs-mke2fs \
	e2fsprogs-e2fsck \
	fakelocale \
	libavahi-client \
	libcrypto-compat \
	ntpdate \
	opkg \
	sdparm \
	task-base \
	task-core-boot \
	tzdata \
	util-linux-sfdisk \
	volatile-media \
	vsftpd \
	hddtemp \
	dosfstools \
	ntfs-3g \
	task-base-smbfs \
	task-base-nfs \
	"

ENIGMA2_PLUGINS = "\
	enigma2-plugin-extensions-graphmultiepg \
	enigma2-plugin-extensions-mediaplayer \
	enigma2-plugin-extensions-cutlisteditor \
	enigma2-plugin-systemplugins-videotune \
	enigma2-plugin-extensions-dvdplayer \
	enigma2-plugin-systemplugins-autoresolution \
	enigma2-plugin-systemplugins-softwaremanager \
	enigma2-plugin-systemplugins-hotplug \
	enigma2-plugin-systemplugins-devicemanager \
	enigma2-plugin-systemplugins-swapmanager \
	gb-multiquickbutton \
	enigma2-plugin-extensions-zaphistorybrowser \
"

ENIGMA2_PLUGINS_append_gbquad = "webbrowser-utils enigma2-plugin-extensions-webbrowser"

export IMAGE_BASENAME = "openmips-image"
IMAGE_LINGUAS = ""

IMAGE_FEATURES += "package-management"

inherit image

rootfs_postprocess() {
			curdir=$PWD
			cd ${IMAGE_ROOTFS}

			# because we're so used to it
			ln -s opkg usr/bin/ipkg || true
			ln -s opkg-cl usr/bin/ipkg-cl || true

			cd $curdir
}

ROOTFS_POSTPROCESS_COMMAND += "rootfs_postprocess; "

export NFO = '${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.nfo'

generate_nfo() {
			VER=`grep Version: "${IMAGE_ROOTFS}/usr/lib/ipkg/info/enigma2.control" | cut -b 10-26`
			echo "Enigma2: ${VER}" > ${NFO}
			echo "Machine: ${MACHINE}" >> ${NFO}
			DATE=`date +%Y-%m-%d' '%H':'%M`
			echo "Date: ${DATE}" >> ${NFO}
			echo "Issuer: openMips Team" >> ${NFO}
			echo "Link: ${DISTRO_FEED_URI}" >> ${NFO}
			if [ "${DESC}" != "" ]; then
					echo "Description: ${DESC}" >> ${NFO}
					echo "${DESC}" >> ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.desc
			fi
			MD5SUM=`md5sum ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.nfi | cut -b 1-32`
			echo "MD5: ${MD5SUM}" >> ${NFO}
}

do_rootfs_append() {
			generate_nfo
}
