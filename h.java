import java.util.Stack;
import java.util.HashMap;
import java.util.Scanner;
public class h {

	//static Stack<ActivationRecord> records;
	
	//-----------Function Numbers && Return Addresses----------------
	//main : -1
	//h : 0
	////z = x + 1 : 0
	////result from nested g() : 1
	////compute nested g(): 2
	////else z + g(h(x-1,y)) : 3
	//g : 1
	////z = y + 1 : 0
	////else z + f(w-1) : 1
	//f : 2
	////then 0 : 0
	////compute nested g : 1
	////z + x + g(w-1) : 2
	
	public static void main(String[] args) {
		int a = 0;
		int b = 0;
		
		//get then initial input values
//		try
//		{
//			a = Integer.parseInt(args[0]);
//			b = Integer.parseInt(args[1]);
//		}
//		catch(Exception e)
//		{
//			System.err.println("Invalid argument");
//		}
		
		Scanner in = new Scanner(System.in);
		a = in.nextInt();
		b = in.nextInt();
			
		//store the initial values in a hash map/dictionary
		HashMap<Character, Integer> ogSymbols = new HashMap<Character, Integer>();
		ogSymbols.put('x', a);
		ogSymbols.put('y', b);
		
		//Initialize an 'empty' activation record to store the final result
		ActivationRecord mainAR = new ActivationRecord();
		//Initialize the initial 'h' record
		ActivationRecord top = new ActivationRecord(0, ogSymbols, -1, mainAR, null);
		
		int returnAddress = 0;
		ActivationRecord previous = new ActivationRecord();
		
		//if(top.CallerActivationRecord != null) System.out.println("CAR is not null");
		
		while(top.CallerActivationRecord != null)
		{
			switch(top.function)
			{
			case 0:
				top.addSymbol('z', top.getValueFromSymbol('x') + 1);
				//determine which "section" of h to return to
				switch(returnAddress)
				{
				//starting h() from the beginning
				case 0:
					//if x == 0 then g y
					if(top.getValueFromSymbol('x') == 0)
					{
						HashMap<Character, Integer> gInit = map(new char[] {'w'}, new int[] {top.getValueFromSymbol('y')});
						top = new ActivationRecord(1, gInit, 1, top, top);
					}
					//else z + g(h(x-1,y))
					else
					{
						HashMap<Character, Integer> hInit = map(new char[] {'x', 'y'}, new int[] {top.getValueFromSymbol('x') - 1, top.getValueFromSymbol('y')});
						top = new ActivationRecord(0, hInit, 2, top, null);
					}
					//Return address is already 0 so it doesn't have to be changed
					break;
				//returning to get the result of g y
				case 1:
					//Going to be finishing this function so we have to set up a return
					//previous is g y
					top.Result = previous.Result;
					previous = top;
					top = top.CallerActivationRecord;
					returnAddress = previous.ReturnAddress;
					break;
				//finished h(x-1,y), now computing g(h(x-1,y))
				case 2:
					int hResult = previous.Result;
					HashMap<Character, Integer> gInit = map(new char[] {'w'}, new int[] {hResult});
					top = new ActivationRecord(1, gInit, 3, top, top);
					
					//Has to be 0 so that g() starts from the beginning
					returnAddress = 0;
					break;
				//computing the result of z + g(h(x-1,y))
				case 3:
					top.Result = top.getValueFromSymbol('z') + previous.Result;
					previous = top;
					top = top.CallerActivationRecord;
					returnAddress = previous.ReturnAddress;
					break;
				}
				break;
			//g()
			case 1:
				switch(returnAddress)
				{
				//starting g() from the beginning
				case 0:
					top.addSymbol('z', top.getValueFromSymbol('y') + 1);
					if(top.getValueFromSymbol('w') == 0)
					{
						top.Result = top.getValueFromSymbol('x');
						previous = top;
						top = top.CallerActivationRecord;
						returnAddress = previous.ReturnAddress;
					}
					else
					{
						HashMap<Character, Integer> fInit = map(new char[] {'x'}, new int[] {top.getValueFromSymbol('w') - 1});
						top = new ActivationRecord(2, fInit, 1, top, top);
					}
					//returnAddress is already 0 so it doesn't have to be set
					break;
				//finished f(w-1), get the final result
				case 1:
					top.Result = top.getValueFromSymbol('z') + previous.Result;
					previous = top;
					top = top.CallerActivationRecord;
					returnAddress = previous.ReturnAddress;
					break;
				}
				break;
			//f()
			case 2:
				switch(returnAddress)
				{
				//starting f() from the beginning
				case 0:
					//returns 0
					if(top.getValueFromSymbol('x') == 0)
					{
						top.Result = 0;
						previous = top;
						top = top.CallerActivationRecord;
						returnAddress = previous.ReturnAddress;
					}
					//start g(w-1)
					else
					{
						HashMap<Character, Integer> gInit = map(new char[] {'w'}, new int[] {top.getValueFromSymbol('w') - 1});
						top = new ActivationRecord(1, gInit, 1, top, top.NestingLink);
					}
					break;
				//return z + x + g(w-1)
				case 1:
					int result = top.getValueFromSymbol('x') + top.getValueFromSymbol('z') + previous.Result;
					top.Result = result;
					previous = top;
					top = top.CallerActivationRecord;
					returnAddress = previous.ReturnAddress;
					break;
				}
				break;
			}
		}
		
		
		System.out.println("h(" + a + ", " + b + ") = " + previous.Result);
	}

	//Helper function for creating and initializing HashMaps
	private static HashMap<Character, Integer> map(char[] c, int[] v)
	{
		HashMap<Character, Integer> result = new HashMap<Character, Integer>();
		for(int i = 0; i < c.length; i++)
		{
			result.put(c[i], v[i]);
		}
		return result;
	}

}
