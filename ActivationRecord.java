import java.util.HashMap;
public class ActivationRecord {

	
	public HashMap<Character, Integer> Symbols;
	public int function;
	public int ReturnAddress;
	public ActivationRecord CallerActivationRecord;
	public ActivationRecord NestingLink;
	public int Result;
	
	public ActivationRecord()
	{
		this.function = -1;
		this.Symbols = new HashMap<Character, Integer>();
		this.ReturnAddress = -1;
		this.CallerActivationRecord = null;
		this.NestingLink = null;
	}
	
	public ActivationRecord(int funName, HashMap<Character, Integer> sym, int rAddress, ActivationRecord cAR, ActivationRecord nest)
	{
		this.function = funName;
		this.Symbols = sym;
		this.ReturnAddress = rAddress;
		this.CallerActivationRecord = cAR;
		this.NestingLink = nest;
	}
	
	public int getValueFromSymbol(char symbol) throws IllegalArgumentException
	{
		if(Symbols.containsKey(symbol))
		{
			return Symbols.get(symbol);
		}
		else
		{
			if(this.NestingLink == null) throw new IllegalArgumentException();
			return this.NestingLink.getValueFromSymbol(symbol);
		}
	}
	
	public void setValueOfSymbol(char symbol, int value) throws IllegalArgumentException
	{
		if(Symbols.containsKey(symbol))
		{
			Symbols.put(symbol, value);
		}
		else
		{
			if(this.NestingLink == null) throw new IllegalArgumentException();
			this.NestingLink.setValueOfSymbol(symbol, value);
		}
	}
	
	public void addSymbol(char symbol, int value)
	{
		try {
			getValueFromSymbol(symbol);
			setValueOfSymbol(symbol, value);
		}catch(IllegalArgumentException e)
		{
			Symbols.put(symbol, value);
		}
	}
	
	
	
}
