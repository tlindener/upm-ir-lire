package es.upm.ir.lire.extension;

public class ObjectRecognitionResult {
	public ObjectRecognitionResult(String label, float probability) {

		this.label = label;
		this.probability = probability;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public float getProbability() {
		return probability;
	}
	public void setProbability(float probability) {
		this.probability = probability;
	}
	String label;
	float probability;

}
