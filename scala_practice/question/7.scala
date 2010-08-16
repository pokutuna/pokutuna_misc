format("%x, %d, %o", 16,16,16)
"%x, %d, %o".format(16,16,16)
//10, 16, 20

"[%8s]".format("hoge") //[    hoge]
"[%.4s]".format("my name is pokutuna")//[my n]
"[%15s]".format("foobar")//[         foobar]
"[%-15s]".format("foobar")//[foobar         ]
"[%04d]".format(32)//[0032]
"[%4.2f]".format(0123.4567)//[123.46]
"[%5.3e]".format(10000.0)//[1.000e+04]
"[%5.2e]".format(0.000000000000000000000001)//[1.00e-24]
"[%+d]".format(-10)//[-10]

//BigInt
var n:BigInt = Integer.MAX_VALUE
"%d".format(n)//2147483647
n *= 10
"%d".format(n)//21474836470

//Number literal
10//Int =10
0xFF//Int = 255
0xabcd//Int = 43981
println(0xFF)//255
012//Int = 10
2147483648l//Long = 2147483648
0.//Double = 0.0
 .0//Double = 0.0 
//行頭だとerror: ';' expected but double literal found.
3E5//Double = 300000.0
3e5//Double = 300000.0
3.e5//Double = 300000.0
3.e+3//Double = 3000.0
3.e-3//Double = 0.003

3.14//Double = 3.14
3.14f//Float = 3.14
3.14d//Double = 3.14
3.14F//Float = 3.14
3.14D//Float = 3.14

//Char literal
'A'//Char = A
'\u0041'//Char = A
'A' == '\u0041'//Boolean = true
'姉'//Char = '姉
'A'.asDigit//10
'B'.asDigit//11
'A'.getDirectionality
