MODULE = "OpenWebif"
PRINC = "4"
DEPENDS += "enigma2"

SRC_URI = "git://github.com/oe-alliance/e2openplugin-${MODULE}.git;protocol=git"

S="${WORKDIR}/git"

python do_package_prepend () {
	boxtypes = [
		('dm500hd', 'dm500hd.jpg', 'dm_normal.png'),
		('dm7020hd', 'unknown.jpg', 'dm_normal.png'),
		('dm8000', 'dm8000.jpg', 'dm_normal.png'),
		('dm800se', 'dm800se.jpg', 'dm_normal.png'),
		('dm800', 'unknown.jpg', 'dm_normal.png'),
		('et5x00', 'et5x00.jpg', 'et_rc5_normal.png'),
		('et6x00', 'et5x00.jpg', 'et_rc5_normal.png'),
		('et9x00', 'et9x00.jpg', 'et_rc7_normal.png'),
		('odinm9', 'et9x00.jpg', 'et_rc7_normal.png'),
		('tmtwin', 'twin.jpg', 'tm_twin.png'),
		('vuduo', 'duo.jpg', 'vu_normal.png'),
		('vusolo', 'solo.jpg', 'vu_normal.png'),
		('vuultimo', 'ultimo.jpg', 'vu_ultimo.png'),
		('vuuno', 'uno.jpg', 'vu_normal.png'),
		('gb800se', 'gb800se.jpg', 'gigablue_black.png'),
		('gb800solo', 'gb800solo.jpg', 'gigablue_black.png'),
		('gb800ue', 'gb800ue.jpg', 'gigablue_black.png'),
		('gbquad', 'gbquad.jpg', 'gigablue_black.png'),
		('ventonhdx', 'ini-3000.jpg', 'ini-3000.png'),
	]
	import os
	top = '${D}${PLUGINPATH}/public/images/'
	for x in boxtypes:
		if x[0] == '${MACHINE}':
			target_box = x[1]
			target_remote = x[2]
			break
	for root, dirs, files in os.walk(top + 'boxes', topdown=False):
		for name in files:
			if target_box != name and name != 'unknown.jpg':
				if target_box == 'ini-3000.jpg' and (name != 'ini-3000.jpg' or name != 'ini-5000.jpg' or name != 'ini-7000.jpg'):
					os.remove(os.path.join(root, name))
				else:
					os.remove(os.path.join(root, name))
	for root, dirs, files in os.walk(top + 'remotes', topdown=False):
		for name in files:
			if target_remote != name and name != 'ow_remote.png':
				if target_remote == 'ini-3000.png' and (name != 'ini-3000.png' or name != 'ini-5000.png' or name != 'ini-7000.png'):
					os.remove(os.path.join(root, name))
				else:
					os.remove(os.path.join(root, name))
}
