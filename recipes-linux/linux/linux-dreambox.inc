require linux.inc

INC_PR = "r11"

PACKAGES_DYNAMIC = "kernel-*"

# For packages that RDEPEND on particular kernel modules, list the ones built into
# the kernel here, so that it is known that the kernel has them built in.
KERNEL_BUILTIN_MODULES = ""

KERNEL_BUILTIN_MODULES_dm8000 = "\
	kernel-module-aes-generic \
	kernel-module-crc32c \
	kernel-module-fuse \
	kernel-module-mac80211 \
	kernel-module-cfg80211 \
	kernel-module-ath \
	kernel-module-ath5k \
	kernel-module-sr-mod \
	kernel-module-isofs \
	kernel-module-udf \
	kernel-module-stv0299 \
	"
KERNEL_BUILTIN_MODULES_dm800se = "\
	kernel-module-fuse \
	kernel-module-stv0299 \
	"
KERNEL_BUILTIN_MODULES_dm7020hd = "\
	kernel-module-fuse \
	kernel-module-stv0299 \
	"
KERNEL_BUILTIN_MODULES_dm500hd = "\
	kernel-module-fuse \
	"

# By default, kernel.bbclass modifies package names to allow multiple kernels
# to be installed in parallel. We revert this change and rprovide the versioned
# package names instead, to allow only one kernel to be installed.
PKG_kernel-base = "kernel-base"
PKG_kernel-image = "kernel-image"
RPROVIDES_kernel-base = "kernel-${KERNEL_VERSION}"
RPROVIDES_kernel-image = "kernel-image-${KERNEL_VERSION} ${KERNEL_BUILTIN_MODULES}"

USB_ROOT = "/dev/sdb2"
USB_ROOT_dm8000 = "/dev/sdf2"

do_install_append() {
	${STRIP} ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
	gzip -9 ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
	echo "/boot/bootlogo-${MACHINE}.elf.gz filename=/boot/bootlogo-${MACHINE}.jpg" > ${D}/${KERNEL_IMAGEDEST}/autoexec.bat
	echo "/boot/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}.gz ${CMDLINE}" >> ${D}/${KERNEL_IMAGEDEST}/autoexec.bat
	echo "/boot/bootlogo-${MACHINE}.elf.gz filename=/boot/bootlogo-${MACHINE}.jpg" > ${D}/${KERNEL_IMAGEDEST}/autoexec_${MACHINE}.bat
	echo "/boot/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}.gz ${USB_CMDLINE}" >> ${D}/${KERNEL_IMAGEDEST}/autoexec_${MACHINE}.bat
}

FILES_kernel-image += "/${KERNEL_IMAGEDEST}/autoexec*.bat"

pkg_preinst_kernel-image() {
	if [ -z "$D" ]
	then
		if mountpoint -q /${KERNEL_IMAGEDEST}
		then
			mount -o remount,rw,compr=none /${KERNEL_IMAGEDEST}
		else
			mount -t jffs2 -o rw,compr=none mtd:'boot partition' /${KERNEL_IMAGEDEST}
		fi
	fi
}
pkg_prerm_kernel-image() {
	if [ -z "$D" ]
	then
		if mountpoint -q /${KERNEL_IMAGEDEST}
		then
			mount -o remount,rw,compr=none /${KERNEL_IMAGEDEST}
		else
			mount -t jffs2 -o rw,compr=none mtd:'boot partition' /${KERNEL_IMAGEDEST}
		fi
	fi
}
pkg_postinst_kernel-image() {
	if [ -z "$D" ]
	then
		umount /${KERNEL_IMAGEDEST}
	fi
}
pkg_postrm_kernel-image() {
	if [ -z "$D" ]
	then
		umount /${KERNEL_IMAGEDEST}
	fi
}

# Do not use update-alternatives!
pkg_postinst_kernel () {
}
pkg_postrm_kernel () {
}

CMDLINE = "root=/dev/mtdblock3 rootfstype=jffs2 rw ${CMDLINE_CONSOLE}"
USB_CMDLINE = "root=${USB_ROOT} rootdelay=10 rw ${CMDLINE_CONSOLE}"

LOCALVERSION = "-${MACHINE}"
