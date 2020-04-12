package com.example.appexperto2020.control;

import java.io.File;

public class PhotoAdapter {

    private File file;
    public PhotoAdapter(File file)
    {
        this.file = file;

    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
