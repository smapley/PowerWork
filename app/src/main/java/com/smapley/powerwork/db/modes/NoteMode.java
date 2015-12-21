package com.smapley.powerwork.db.modes;


import com.smapley.powerwork.db.entity.NoteDetailsEntity;
import com.smapley.powerwork.db.entity.NoteEntity;

import java.util.List;

public class NoteMode {

	private NoteEntity noteEntity;

	private List<NoteDetailsEntity> listnoteDetailsEntities;




	

	public NoteEntity getNoteEntity() {
		return noteEntity;
	}

	public void setNoteEntity(NoteEntity noteEntity) {
		this.noteEntity = noteEntity;
	}

	public List<NoteDetailsEntity> getListnoteDetailsEntities() {
		return listnoteDetailsEntities;
	}

	public void setListnoteDetailsEntities(
			List<NoteDetailsEntity> listnoteDetailsEntities) {
		this.listnoteDetailsEntities = listnoteDetailsEntities;
	}
	
	
}