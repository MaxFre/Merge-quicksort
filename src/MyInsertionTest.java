import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;


/**
 * Stor och klyddig klass, hade inte lust att separera in till egna klasser. sorry.
 * @author Max Frennessen
 *
 */

public class MyInsertionTest {
	private static byte[] aux;
	
	
    static void insertionSort(byte[] a, int lo, int hi) {
//    	System.out.println("-insertionSort-");
        for (int i = lo; i <= hi; i++) {														//  100 000  4,562  
        																						//  200 000 18,119	   ~3,94
            for (int j = i; j > lo && a[j] < a[j-1]; j--) {										//  400 000 71,455     ~3,94
                byte x = a[j]; a[j] = a[j-1]; a[j-1] = x;										//  800 000  288,689   ~3,94
            }
        }
    }
    
    
    //MERGESORT!
	public static void sort(byte[] a) {
		aux = new byte[a.length];
		Msort(a, 0, a.length-1);
	}
	
    private static void Msort(byte[] a, int lo, int hi) {
		if(hi <= lo+50) {
			insertionSort(a, lo, hi);
			return;
		}
//		if(hi <= lo)
//			return;
		int mid = lo + (hi-lo)/2;  
		Msort(a, lo, mid);
		Msort(a, mid+1, hi);
		merge(a, lo, mid, hi);

	}


	private static void merge(byte[] a, int lo, int mid, int hi) {
		
		for(int k = lo; k <= hi; k++) {
			aux[k] = a[k];
		}
		
		int i = lo;
		int j = mid+1;
		
		for(int k = lo; k <= hi; k++) {
			if(i > mid)					a[k] = aux[j++];
			else if (j > hi)			a[k] = aux[i++];
			else if (aux[j] < aux[i])	a[k] = aux[j++];
			else 						a[k] = aux[i++];
				
		}
	}
	
		//QUICKSORT!
	
		static Random rand;
				
		public static void Qsort(byte[] a) {
			rand = new Random();
			//shuffle(a, 0, a.length-1);
			quickSort(a, 0, a.length-1);
		}
		
	    private static void quickSort(byte[] a, int lo, int hi) {
	    	int r = lo + rand.nextInt(hi+1-lo);
			if(hi <= lo+100) {
				insertionSort(a, lo, hi);
				return;
			}
	    	if(hi <= lo)
	    		return;
			int i = lo;
			int j = hi;
			int p = a[r];
			while(i <= j) {
				while(a[i] < p) {
					i++;
				}
				while(a[j] > p) {
					j--;
				}
				if(i <= j) {
					exch(a, i, j);
					i++;
					j--;
				}
			}
			if(lo < j)
				quickSort(a,lo,j);
			if(i < hi)
				quickSort(a, i, hi);
			
		}

		private static void exch(byte[] a,int i, int j) {
			byte temp = a[i];
			a[i] = a[j];
			a[j] = temp;
			
		}
	
    


    // Checks if the first n element of a are in sorted order.
    private static boolean isSorted(byte[] a, int lo, int hi) {
        int flaws = 0;
        for (int i = lo+1; i <= hi; i++) {
            if (a[i] < a[i-1]) {
                if (flaws++ >= 10) {
                    System.out.println("...");
                    break;
                }
                System.out.println("a["+i+"] = "+a[i]+", a["+(i-1)+"] = "+a[i+1]);
            }
        }
        return flaws == 0;
    }

    // Shuffles the first n elements of a.
    public static void shuffle(byte[] a, int lo, int hi) {
        Random rand = new Random();
        for (int i = lo; i <= hi; i++) {
            int r = i + rand.nextInt(hi+1-i);     // between i and hi
            byte t = a[i]; a[i] = a[r]; a[r] = t;
        }
    }

    public static void main(String[] args) throws Exception {
        byte[] encoded = Files.readAllBytes(Paths.get("bible-en.txt"));
      
        int N =  encoded.length;     //encoded.length; // Change to some number to test on part of array.
        System.out.println(N);
        shuffle(encoded, 0 , N-1);  //shuffles the txt.
        
        long before = System.currentTimeMillis();
//        insertionSort(encoded, 0, N-1);
//        Qsort(encoded);
        sort(encoded);
        long after = System.currentTimeMillis();
        
        // Write sorted to file, in case we want to check it.
        Files.write(Paths.get("processed.txt"), encoded);    
               

        if (isSorted(encoded, 0, N-1)) {
            System.out.println((after-before) / 1000.0 + " seconds");
        }
        

    }
}