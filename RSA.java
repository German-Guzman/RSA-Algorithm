package Encryption;
import java.math.BigInteger;
import java.util.Scanner;

public class RSA {

public static void main(String[] args) 
{
	BigInteger Pt,Ct,p,q,c;
	//BigInteger C = BigInteger.valueOf(5);
	//BigInteger e = BigInteger.valueOf(3);
	
Scanner input = new Scanner(System.in);
System.out.print("Enter p:");
p = input.nextBigInteger();

System.out.print("Enter q:");
q = input.nextBigInteger();

System.out.print("Enter value of text:");
c = input.nextBigInteger();

BigInteger pi= p.subtract(BigInteger.ONE); //p-1
BigInteger qi= q.subtract(BigInteger.ONE); //q-1

BigInteger n = p.multiply(q); 
//System.out.println("Value of n: " + n);
BigInteger ni = pi.multiply(qi); //totient

//System.out.println("Value of (p-1)*(q-1):"+ ni );
//System.out.println("value of q is:" + q);
//System.out.println("value of p is:" + p);
//System.out.println("value of q-1 is:" + qi);
//System.out.println("value of p-1 is:" + pi);

int Enum = 0;  // counter for e values
long Encmin=0; // encryption timer minimum
long Decmin=0; // decryption timer minimum
BigInteger Enclowe = BigInteger.ZERO; //variable initialization to get the combination of e and d with lowest Encrypt and Decryption time.
BigInteger Enclowd = BigInteger.ZERO;
BigInteger Declowe = BigInteger.ZERO;
BigInteger Declowd = BigInteger.ZERO;

int ni1;
ni1 = ni.intValue(); // int value for BigInt to be able to use in the for loop. ni being the totient

for (int i=2; i <= ni1; i++) {	// 2 to totient
	BigInteger e = BigInteger.valueOf(i);
	BigInteger gcdee = e.gcd(ni); //gcd of e and totient
	if (gcdee.equals(BigInteger.ONE)) {  // Compute when gcd of e and totient = 1 
		BigInteger d = e.modInverse(ni); // d such that e*d=1mod(p−1)*(q−1)
		
		long startTime = System.nanoTime();
		/* … The code being measured starts … */
		Ct= c.modPow(e,n);
		/* … The code being measured ends … */
		long endTime = System.nanoTime();
		// get the difference between the two nano time values
		long EncTime = endTime - startTime;
		//System.out.println("Encrytion time  nanoseconds: " + EncTime);
		
		long startTime1 = System.nanoTime();
		/* … The code being measured starts … */
		Pt= Ct.modPow(d,n); // C^d(mod n)
		/* … The code being measured ends … */
		long endTime1 = System.nanoTime();
		// get the difference between the two nano time values
		long DecTime = endTime1 - startTime1;
		//System.out.println("e= "+ e);
		//System.out.println("d= "+ d);
		//System.out.println("Cipher text: "+ Ct); //encrypt
		//System.out.println("Plaintext: "+ Pt); //decrypt
		System.out.println("["+ e +","+ d +","+ Ct+","+Pt+","+EncTime+","+DecTime+ "]");
		if (Enum == 0) 
			Decmin= Encmin= EncTime;
		else {
		    if (EncTime < Encmin) {
		    	Encmin = EncTime;
		    	Enclowe = e;
		    	Enclowd = d;
		    }
		    if(DecTime < Decmin) {
		    	Decmin=DecTime;
		    	Declowe= e;
		    	Declowd= d;
		    }
		}
		Enum++;
	}
}
System.out.println("Total # of e values:" + Enum);
System.out.println("Keys with Shortest enc time of " + Encmin +"ns, e= "+ Enclowe +" d= "+ Enclowd);
System.out.println("Keys with Shortest dec time of " + Decmin +"ns, e= "+ Declowe +" d= "+ Declowd);

}
}

// Updated and Finished ! 