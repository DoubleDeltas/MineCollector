package com.doubledeltas.minecollector.resource;

import com.doubledeltas.minecollector.McolInitializable;
import com.doubledeltas.minecollector.MineCollector;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ResourceManager implements McolInitializable {
    private MineCollector plugin;
    private ClassLoader classLoader;

    @Override
    public void init(MineCollector plugin) {
        this.plugin = plugin;
        this.classLoader = getClassLoader(plugin);
    }

    public void copyDirectory(String resourcePath, File targetPath) {
        File targetParent = targetPath.getParentFile();
        if (!targetPath.exists() || !targetPath.isDirectory())
            targetPath.mkdirs();

        JarFile jarFile;
        try {
            JarURLConnection conn = (JarURLConnection) classLoader.getResource(resourcePath).openConnection();
            jarFile = conn.getJarFile();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        Enumeration<JarEntry> entries = jarFile.entries();
        JarEntry entry;
        while (entries.hasMoreElements()) {
            entry = entries.nextElement();
            String entryName = entry.getName();
            if (!entryName.startsWith(resourcePath))
                continue;
            File targetFile = new File(targetParent, entryName);
            if (entry.isDirectory()) {
                targetFile.mkdirs();
            }
            else {
                try (FileOutputStream fos = new FileOutputStream(targetFile)){
                    jarFile.getInputStream(entry).transferTo(fos);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    // using reflection
    private static ClassLoader getClassLoader(JavaPlugin plugin) {
        try {
            Method getter = JavaPlugin.class.getDeclaredMethod("getClassLoader");
            getter.setAccessible(true);
            return (ClassLoader) getter.invoke(plugin);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
