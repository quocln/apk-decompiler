package com.danwatling.apkdecompiler.steps;

import com.danwatling.apkdecompiler.Logger;
import com.danwatling.apkdecompiler.adb.Adb;
import com.danwatling.apkdecompiler.adb.AndroidPackage;

import java.util.List;

/**
 * Pulls Apk from device
 */
public class FetchApk extends BaseStep {
	private String filter = null;
	private String apkFile = null;
	private Adb adb = null;

	public FetchApk(String filter, String apkFile) {
		this.filter = filter;
		this.apkFile = apkFile;

		adb = new Adb();
	}

	public boolean run() {
		boolean result = true;

		try {
			List<AndroidPackage> packages = adb.listPackages(this.filter);

			if (packages.size() > 1) {
				Logger.info("Found " + packages.size() + " applications that match: " + this.filter);
				for (AndroidPackage p : packages) {
					Logger.info("\t" + p.getPath() + " - " + p.getPackage());
				}
			}

			adb.pull(packages.get(0), this.apkFile);
		} finally {
			adb.exit();
		}

		return result;
	}
}