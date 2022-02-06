package com.example.drawer_activity;

public class NotesModel {
    String noteText,noteId;

    public NotesModel(String noteText, String noteId) {
        this.noteText = noteText;
        this.noteId = noteId;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }
}
