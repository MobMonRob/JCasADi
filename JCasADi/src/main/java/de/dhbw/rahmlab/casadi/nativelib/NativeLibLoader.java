package de.dhbw.rahmlab.casadi.nativelib;

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
			de.dhbw.rahmlab.nativelibloader.api.NativeLibLoader.init(false);
			de.dhbw.rahmlab.nativelibloader.api.NativeLibLoader nativeLibLoader = de.dhbw.rahmlab.nativelibloader.api.NativeLibLoader.getInstance();
			nativeLibLoader.load(NativeLibLoader.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
