package org.stlpriory.robotics.utils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Objects;

/**
 * Utility methods to make working with files a bit easier
 *
 * @see java.util.File
 */
public class FileUtils {
    public static final String DIR_TEMP = System.getProperty("java.io.tmpdir"); //$NON-NLS-1$

    /**
     * Return the temporary directory {@link File} specified by the
     * java.io.tmpdir system property
     *
     * @return the temporary directory {@link File}
     */
    public static File getSystemTempDirectory() {
        Path path = Paths.get(DIR_TEMP);
        if (Files.notExists(path)) {
            try {
                return Files.createDirectories(path).toFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new File(DIR_TEMP);
    }

    /**
     * Create the named {@link File} under the directory specified in the {@link File}. If
     * the file already exists and exception will be thrown.
     *
     * @param fileName the name of the file
     * @param baseDir  the parent directory
     * @return the created {@link File}
     * @throws IOException if an error occurs while creating the {@link File}
     */
    public static File createFile(final String fileName, final File baseDir) throws IOException {
        Objects.requireNonNull(fileName, "The file name may not be null");
        Objects.requireNonNull(baseDir, "The directory may not be null");
        Path path = Paths.get(baseDir.getAbsolutePath(), fileName);
        return Files.createFile(path).toFile();
    }

    /**
     * Creates a directory by creating all nonexistent parent directories first.
     * The method will not be thrown if the directory could not be created because it
     * already exists.
     *
     * @param dirPath the directory path to create
     * @return the created directory {@link File}
     * @throws IOException if an error occurs while creating the {@link File}
     */
    public static File createDirectories(final String dirPath) throws IOException {
        return createDirectories(new File(dirPath));
    }

    /**
     * Creates a directory by creating all nonexistent parent directories first.
     * The method will not be thrown if the directory could not be created because it
     * already exists.
     *
     * @param dir the directory path to create
     * @return the created directory {@link File}
     * @throws IOException if an error occurs while creating the {@link File}
     */
    public static File createDirectories(final File dir) throws IOException {
        Path path = Objects.requireNonNull(dir.toPath(), "The directory7 may not be null");
        return Files.createDirectories(path).toFile();
    }

    /**
     * Create a temporary file under the location specified by the
     * java.io.tmpdir system property
     *
     * @return the created temporary {@link File}
     * @throws IOException if an error occurs while creating the {@link File}
     */
    public static File createTempFile() throws IOException {
        return Files.createTempFile(Paths.get(DIR_TEMP), "tmp_", null).toFile();
    }

    /**
     * Create a temporary file under the specified base directory.
     *
     * @param baseDir the base directory {@link File}
     * @return the created temporary {@link File}
     * @throws IOException if an error occurs while creating the {@link File}
     */
    public static File createTempFile(final File baseDir) throws IOException {
        Path path = Objects.requireNonNull(baseDir.toPath(), "The directory may not be null");
        return Files.createTempFile(path, "tmp_", null).toFile();
    }

    /**
     * Create a temporary directory under the location specified by the
     * java.io.tmpdir system property
     *
     * @return the path to the directory
     * @throws IOException if an error occurs while creating the {@link File}
     */
    public static File createTempDirectory() throws IOException {
        return Files.createTempDirectory(Paths.get(DIR_TEMP), "tmp_").toFile();
    }

    /**
     * Create a temporary directory under the specified base directory.
     *
     * @param baseDir the base directory {@link File}
     * @return the created temporary {@link File}
     * @throws IOException if an error occurs while creating the {@link File}
     */
    public static File createTempDirectory(final File baseDir) throws IOException {
        Path path = Objects.requireNonNull(baseDir.toPath(), "The directory may not be null");
        return Files.createTempDirectory(path, "tmp_").toFile();
    }

    /**
     * Read all content from a file and return it into a single string. Bytes from the file are
     * decoded into characters using the UTF-8 charset. This quick method is targeted at reading
     * small byte/text files in a single shot. The underlying logic reads the entire file's bytes
     * or lines, respectively, into a single read and takes care of opening and closing the stream
     * for you after the file has been read or an I/O error or exception has occurred.
     *
     * @param file the {@link File} to read from
     * @return the content from the file as a single string.
     * @throws IOException if an error occurs while reading the {@link File}
     */
    public static String readContent(final File file) throws IOException {
        Path path = Objects.requireNonNull(file.toPath(), "The file may not be null");
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }

    /**
     * Read all lines from a file. Bytes from the file are decoded into characters
     * using the the UTF-8 charset. This quick method is targeted at reading small byte/text
     * files in a single shot. The underlying logic reads the entire file's bytes or lines,
     * respectively, into a single read and takes care of opening and closing the stream for
     * you after the file has been read or an I/O error or exception has occurred.
     *
     * @param file the {@link File} to read from
     * @return the lines from the file as an unmodifiable List
     * @throws IOException if an error occurs while reading the {@link File}
     */
    public static List<String> readAllLines(final File file) throws IOException {
        Path path = Objects.requireNonNull(file.toPath(), "The file may not be null");
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }

    /**
     * Write lines of text to a file. Characters are encoded into bytes using
     * the UTF-8 charset.
     *
     * @param file  the {@link File} to write to
     * @param lines the file content
     * @throws IOException if an error occurs while writing the {@link File}
     */
    public static void writeAllLines(final File file, final List<String> lines) throws IOException {
        Path path = Objects.requireNonNull(file.toPath(), "The file may not be null");
        Files.write(path, lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
    }

    /**
     * Write the string of text to a file. Characters are encoded into bytes using
     * the UTF-8 charset.
     *
     * @param file    the {@link File} to write to
     * @param content the file content
     * @throws IOException if an error occurs while writing the {@link File}
     */
    public static void writeContent(final File file, final String content) throws IOException {
        Path path = Objects.requireNonNull(file.toPath(), "The file may not be null");
        Files.write(path, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
    }

    /**
     * Deletes a file if it exists. If the file is a symbolic link, then the
     * symbolic link itself, not the final target of the link, is deleted. If the
     * file is a directory then the directory must be empty.
     *
     * @param file the file/directory {@link File}
     * @param path the path to the file/directory
     * @throws IOException
     */
    public static void delete(final File file) throws IOException {
        Path path = Objects.requireNonNull(file.toPath(), "The file/directory may not be null");
        Files.deleteIfExists(path);
    }

    /**
     * Recursively delete all files and sub-directories under the specified
     * directory (inclusive).
     *
     * @param directory the directory {@link File}
     * @throws IOException
     */
    public static void deleteRecursive(final File directory) throws IOException {
        Path start = Objects.requireNonNull(directory.toPath(), "The directory may not be null");

        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                Files.deleteIfExists(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(final Path dir, final IOException e) throws IOException {
                if (e == null) {
                    Files.deleteIfExists(dir);
                    return FileVisitResult.CONTINUE;
                } else {
                    // directory iteration failed
                    throw e;
                }
            }

            @Override
            public FileVisitResult visitFileFailed(final Path file, final IOException e) {
                System.out.println(e);
                return FileVisitResult.CONTINUE;
            }
        });

        Files.deleteIfExists(start);
    }

    /**
     * Close the specified stream, ignoring any errors. This is useful to call
     * in finally blocks.
     *
     * @param stream The stream to close
     */
    public static void close(final Closeable stream) {
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


}