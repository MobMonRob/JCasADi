package de.dhbw.rahmlab.casadi.nativelib;

import de.dhbw.rahmlab.casadi.impl.casadi.GlobalOptions;
import de.dhbw.rahmlab.nativelibloader.api.NativeLib;
import java.util.List;
import java.util.stream.Collectors;

public class NativeLibLoader {

	private static boolean isLoaded = false;

	public static void load() {
		if (!isLoaded) {
			loadActually();
			isLoaded = true;
		}
	}

	private static void loadActually() {
		try {
			List<NativeLib> loadedLibs = de.dhbw.rahmlab.nativelibloader.api.NativeLibLoader.loadLibs(NativeLibLoader.class);

			String os = System.getProperty("os.name").toLowerCase();
			String delim;
			// In accordance to casadi_os.cpp.
			if (os.contains("windows")) {
				delim = ";";
			} else {
				delim = ":";
			}

			String libPaths = loadedLibs.stream().map(nl -> nl.getPath().getParent()).distinct().map(p -> p.toString()).collect(Collectors.joining(delim));
			GlobalOptions.setCasadiPath(libPaths);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
