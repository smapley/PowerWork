package com.smapley.powerwork.db.mode;


import com.smapley.powerwork.db.entity.MessageEntity;
import com.smapley.powerwork.db.entity.ProjectEntity;
import com.smapley.powerwork.db.entity.TaskEntity;
import com.smapley.powerwork.db.entity.UserEntity;

public class MessageMode {

	private MessageEntity messageEntity;
	private UserEntity userEntity;
	private UserEntity srcUserEntity;
	private TaskEntity taskEntity;
	private ProjectEntity projectEntity;


	public MessageEntity getMessageEntity() {
		return messageEntity;
	}

	public void setMessageEntity(MessageEntity messageEntity) {
		this.messageEntity = messageEntity;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public UserEntity getSrcUserEntity() {
		return srcUserEntity;
	}

	public void setSrcUserEntity(UserEntity srcUserEntity) {
		this.srcUserEntity = srcUserEntity;
	}

	public TaskEntity getTaskEntity() {
		return taskEntity;
	}

	public void setTaskEntity(TaskEntity taskEntity) {
		this.taskEntity = taskEntity;
	}

	public ProjectEntity getProjectEntity() {
		return projectEntity;
	}

	public void setProjectEntity(ProjectEntity projectEntity) {
		this.projectEntity = projectEntity;
	}

}
