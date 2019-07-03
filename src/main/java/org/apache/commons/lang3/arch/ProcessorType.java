package org.apache.commons.lang3.arch;


import org.apache.commons.lang3.arch.Processor.Type;

public class ProcessorType {
	private final Type type;

	public ProcessorType(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	/**
	* Checks if  {@link Processor}  is type of x86.
	* @return  <code>true</code>, if  {@link Processor}  is  {@link Type#X86} , else <code>false</code>.
	*/
	public boolean isX86() {
		return Type.X86.equals(type);
	}

	/**
	* Checks if  {@link Processor}  is type of Intel Itanium.
	* @return  <code>true</code>. if  {@link Processor}  is  {@link Type#IA_64} , else <code>false</code>.
	*/
	public boolean isIA64() {
		return Type.IA_64.equals(type);
	}

	/**
	* Checks if  {@link Processor}  is type of Power PC.
	* @return  <code>true</code>. if  {@link Processor}  is  {@link Type#PPC} , else <code>false</code>.
	*/
	public boolean isPPC() {
		return Type.PPC.equals(type);
	}
}