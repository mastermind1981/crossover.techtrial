package org.pm.crossover.task.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UploadedFile {
    private final int length;
    private final byte[] bytes;
    private final String name;
    private final String type;
}
