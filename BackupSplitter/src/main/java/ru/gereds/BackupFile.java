package ru.gereds;

import ru.gereds.enums.ProgFlags;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class BackupFile {
    private List<NCfile> backUpFile = new ArrayList<NCfile>();
    public BackupFile(String file) {
        this.fill(file);
    }

    private void fill(String file) {
      List<String> lines = new ArrayList<>(Arrays.asList(file.split("\\r?\\n")));
      StringBuilder body = new StringBuilder();
      boolean isFirstLine = true;
      boolean isFirstWorld = true;
        for (int i = 0; i < lines.size(); i++) {
            if (oCounter(lines.get(i)) > 1) {

              for (String world : lines.get(i).split("\\s")) {
                  if (isFirstWorld) {
                      if (body.length() != 0) {
                          this.backUpFile.add(new NCfile(body.toString()));
                          body.setLength(0);
                      }
                      body.append(String.format("%s ", world));
                      isFirstWorld = false;

                  } else {
                      if (world.startsWith(ProgFlags.PROGSTART.flag)) {
                          this.backUpFile.add(new NCfile(body.toString()));
                          body.setLength(0);
                          body.append(String.format("%s ", world));
                      } else {
                          body.append(String.format("%s ", world));
                      }
                  }
              }
            } else {
                if (isFirstLine) {
                    body.append(String.format("%s%n", lines.get(i)));
                    isFirstLine = false;
                } else {
                    if (lines.get(i).startsWith(ProgFlags.PROGSTART.flag)) {
                        this.backUpFile.add(new NCfile(body.toString()));
                        body.setLength(0);
                        body.append(String.format("%s%n", lines.get(i)));
                    } else {
                        body.append(String.format("%s%n", lines.get(i)));
                    }
                }
            }
        }
    this.backUpFile.add(new NCfile(body.toString()));
    body.setLength(0);
    }

    private int oCounter(String file) {
        int counter = 0;
        String[] words = makeCommentLess(file).split("\\r?\\n?\\s");
        for (String word : words) {
            if (word.startsWith(ProgFlags.PROGSTART.flag)) {
                counter++;
            }
        }
        return  counter;
    }

    private String makeCommentLess(String file) {
        StringBuilder result = new StringBuilder();
        boolean comment = false;
        for (int i = 0; i < file.length(); i++) {
            if (file.charAt(i) == '(') {
                comment = true;
            }
            if (file.charAt(i) == ')') {

                comment = false;
            }
            if (!comment && file.charAt(i) != ')') {
                result.append(file.charAt(i));
            }
        }
        return result.toString();
    }

    public NCfile get(int index) {
        return this.backUpFile.get(index);
    }

    public int size() {
       return this.backUpFile.size();
    }
}
