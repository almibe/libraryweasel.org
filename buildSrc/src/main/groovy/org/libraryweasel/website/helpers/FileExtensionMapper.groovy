package org.libraryweasel.website.helpers

class FileExtensionMapper {
    File switchFileExtension(File f, String newExtension) {
        File parent = f.parentFile
        String name = f.name
        int index = name.lastIndexOf('.')
        if(parent != null) {
            return index == -1 ? new File(parent, name + '.' + newExtension) : new File(parent, name.substring(0, index) + '.' + newExtension)
        } else {
            return index == -1 ? new File(name + '.' + newExtension) : new File(name.substring(0, index) + '.' + newExtension)
        }
    }

    String switchFileExtension(String s, String newExtension) {
        return switchFileExtension(new File(s), newExtension).path
    }

    String getExtension(File file) {
        String name = file.name
        int index = name.lastIndexOf('.')
        return index == -1 ? '' : name.substring(index+1)
    }

    String getExtension(String file) {
        return getExtension(new File(file))
    }
}
