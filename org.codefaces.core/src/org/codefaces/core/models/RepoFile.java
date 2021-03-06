package org.codefaces.core.models;

public class RepoFile extends RepoResource {
	public RepoFile(RepoFolderRoot root, RepoResource parent, String id,
			String name) {
		super(root, parent, id, name, RepoResourceType.FILE);
	}
	
	//This method is intended to use only in testing
	protected RepoFile(RepoFolderRoot root, RepoResource parent, String id,
			String name, RepoFileInfo info){
		super(root, parent, id, name, RepoResourceType.FILE, info);
	}

	@Override
	protected RepoFileInfo getInfo() {
		return (RepoFileInfo) super.getInfo();
	}

	@Override
	public boolean hasChildren() {
		return false;
	}

	public String getContent() {
		return getInfo().getContent();
	}

	public String getMimeType() {
		return getInfo().getMimeType();
	}

	public String getMode() {
		return getInfo().getMode();
	}

	public long getSize() {
		return getInfo().getSize();
	}
}
