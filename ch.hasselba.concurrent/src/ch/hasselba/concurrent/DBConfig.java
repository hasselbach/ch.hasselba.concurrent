package ch.hasselba.concurrent;

public class DBConfig {
	private String server;
	private String replicaId;

	public String getServer() {
		return server;
	}

	public String getReplicaId() {
		return replicaId;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public void setReplicaId(String replicaId) {
		this.replicaId = replicaId;
	}

}