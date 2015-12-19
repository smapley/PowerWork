package com.smapley.powerwork.db.mode;

import com.smapley.powerwork.db.entity.TaskDetailsEntity;
import com.smapley.powerwork.db.entity.TaskEntity;

import java.util.List;


public class TaskMode {
	private TaskEntity taskEntity;
	
	private List<TaskDetailsEntity> listTaskDetailsEntities;
	private List<TasUseMode> listTasUseModes;




	public TaskEntity getTaskEntity() {
		return taskEntity;
	}

	public void setTaskEntity(TaskEntity taskEntity) {
		this.taskEntity = taskEntity;
	}

	public List<TaskDetailsEntity> getListTaskDetailsEntities() {
		return listTaskDetailsEntities;
	}

	public void setListTaskDetailsEntities(
			List<TaskDetailsEntity> listTaskDetailsEntities) {
		this.listTaskDetailsEntities = listTaskDetailsEntities;
	}

	public List<TasUseMode> getListTasUseModes() {
		return listTasUseModes;
	}

	public void setListTasUseModes(List<TasUseMode> listTasUseModes) {
		this.listTasUseModes = listTasUseModes;
	}

}
