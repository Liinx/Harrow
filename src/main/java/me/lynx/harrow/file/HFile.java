package me.lynx.harrow.file;

/**
 * Represents ether a configuration file or storage file that is stored
 * in the plugin directory.
 */
public interface HFile {

    String getName();

    java.io.File getFile();

}