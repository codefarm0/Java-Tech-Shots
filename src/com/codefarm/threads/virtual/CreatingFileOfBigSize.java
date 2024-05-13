package com.codefarm.threads.virtual;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.*;

public class CreatingFileOfBigSize {
    private static final long HUGE_FILE_SIZE = 1000000000l;

    public static void main(String[] args) throws IOException {
        final ByteBuffer buf = ByteBuffer.allocate(4).putInt(2);
        buf.rewind();

        final OpenOption[] options = { StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW , StandardOpenOption.SPARSE };
        final Path hugeFile = Paths.get("hugefile.txt");

        try (final SeekableByteChannel channel = Files.newByteChannel(hugeFile, options);) {
            channel.position(HUGE_FILE_SIZE);
            channel.write(buf);
        }
    }

}
