package ru.gereds;

import ru.gereds.enums.Extension;
import ru.gereds.enums.ProgFlags;


/**
 * Class NCProgramme - contains all necessary info about NC programme.
 * Such us Memory name (Oname), BluePrint name (commentName), extention , And programme himself.
 *
 * @author  Bugakov Viatcheslav(rogerkind@mail.ru)
 *
 */

public class NCProgramme {
    private String memoryName; // O name
    private String commentName;
    private Extension extention;
    private String body;
    private static final String NOCOMENT = "no comment";

     public NCProgramme(String body) {
         this.body = body;
         this.commentName = this.findName(ProgFlags.PROGSTART);
         this.memoryName = this.findName(ProgFlags.COMMENTSTART);
         this.extention = Extension.NC;
     }

    public void setExtention(Extension extention) {
        this.extention = extention;
    }

    public String getBody() {
        return body;
    }

    public String getCommentName() {
        return commentName;
    }

    public String getMemoryName() {
        return memoryName;
    }

    public Extension getExtention() {
        return extention;
    }

    public String buildFileName(boolean isMemoryName) {
         String result;
         if (isMemoryName) {
             result = String.format("%s.%s", this.memoryName, this.extention.name);
         } else {
             result = String.format("%s.%s", this.commentName, this.extention.name);
         }
         return result;
    }

    private String findName(ProgFlags flag) {
     String result = "";
        String[] lines = this.body.split("\\r?\\n");
        for (String line : lines) {
            if (flag == ProgFlags.PROGSTART) {
                String[] words = line.split("\\s");
                for (String word : words) {
                    if (word.startsWith(ProgFlags.PROGSTART.flag)) {
                        result = word;
                        break;
                    }
                }
            } else {
                if (line.indexOf(ProgFlags.COMMENTSTART.flag) > 0) {
                    result = line.substring(line.indexOf(ProgFlags.COMMENTSTART.flag) + 1,
                            line.indexOf(ProgFlags.COMMENTEND.flag)).trim().toLowerCase();
                    if (!result.equals("")) {
                        break;
                    }
                } else {
                    result = NOCOMENT;
                }
            }
        }
         return result;
     }



}
