package plain2beamer.documents;

public class ListItem {
	private final int padding;
	private final String bullet;
	private final String text;

	public ListItem(int padding, String bullet, String text) {
		super();
		this.padding = padding;
		this.bullet = bullet;
		this.text = text;
	}

	public int getPadding() {
		return padding;
	}

	public String getBullet() {
		return bullet;
	}

	public String getText() {
		return text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bullet == null) ? 0 : bullet.hashCode());
		result = prime * result + padding;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ListItem other = (ListItem) obj;
		if (bullet == null) {
			if (other.bullet != null)
				return false;
		} else if (!bullet.equals(other.bullet))
			return false;
		if (padding != other.padding)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ListItem [>>" + padding + " :: " + bullet + " :: " + text + "]";
	}

}
