# Nested Functions in Java
Using activation records to implement nested functions in Java.

This program implements the following ML function:

fun h(x, y) =

  let
  
    val z = x + 1
    
    fun g w =
    
      let
      
        val z = y + 1
        
        fun f x =
        
          if (x = 1) then 0
          
          else z + x + g(w - 1)
          
      in
      
        if (w = 0) then x
        
        else z + f(w - 1)
        
  in
  
    if (x = 0) then g y
    
    else z + g(h(x - 1, y))
    
  end;

Here is some sample output for this function:

h(0, 10) = 254

h(0, 11) = 307

h(0, 12) = 365

h(0, 13) = 428

h(0, 14) = 496

h(0, 15) = 569

h(0, 16) = 647

h(0, 17) = 730

h(0, 18) = 818

h(0, 19) = 911

h(0, 20) = 1009

h(1, 10) = 37710

h(1, 11) = 54329

h(1, 12) = 75909

h(1, 13) = 103350

h(1, 14) = 137627

h(1, 15) = 179790

h(1, 16) = 230964

h(1, 17) = 292349

h(1, 18) = 365220

h(1, 19) = 450927

h(1, 20) = 550895
