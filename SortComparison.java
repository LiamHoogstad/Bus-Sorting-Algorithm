class SortComparison {

    /**
     * Sorts an array of doubles using InsertionSort.
     * This method is static, thus it can be called as SortComparison.sort(a)
     * @param a: An unsorted array of doubles.
     * @return array sorted in ascending order.
     *
     */
    public static double[] insertionSort(double[] a){

        //todo: implement the sort
       
        double temp;
        for (int i = 1; i < a.length; i++) {
            for(int j = i ; j > 0 ; j--){
                if(a[j] < a[j-1]){
                    temp = a[j];
                    a[j] = a[j-1];
                    a[j-1] = temp;
                }
            }
        }
        return a;
  

    }//end insertionsort
	
	    /**
     * Sorts an array of doubles using Selection Sort.
     * This method is static, thus it can be called as SortComparison.sort(a)
     * @param a: An unsorted array of doubles.
     * @return array sorted in ascending order
     *
     */
    static double [] selectionSort (double a[]){

        //todo: implement the sort
        int n = a.length;

        for (int i = 0; i < n-1; i++)
        {
            int min_idx = i;
            for (int j = i+1; j < n; j++)
            {
                if (a[j] < a[min_idx])
                min_idx = j;
            }

            double temp = a[min_idx];
            a[min_idx] = a[i];
            a[i] = temp;
        }
        return a;
    }//end selectionsort

    /**
     * Sorts an array of doubles using Quick Sort.
     * This method is static, thus it can be called as SortComparison.sort(a)
     * @param a: An unsorted array of doubles.
     * @return array sorted in ascending order
     *
     */
    static double [] quickSort (double a[]){
	
		 //todo: implement the sort
         recursiveQuick(a, 0, a.length-1);
         return a;

    }//end quicksort

    static void recursiveQuick(double[] numbers, int lo, int hi)
    {
        if(numbers.length == 0) return; //
        if(lo < 0) return;
        if(hi>numbers.length+1)return;

        if(hi <= lo) return;
        int pivotPos = partition(numbers, lo, hi);
        recursiveQuick(numbers, lo, pivotPos-1);
        recursiveQuick(numbers, pivotPos+1, hi);
    }

    static int partition(double[] numbers, int lo, int hi)
    {

        if(numbers.length == 0) return -1; //
        if(lo < 0) return -1;
        if(hi>numbers.length+1)return -1;

        int i = lo;
        int j = hi+1;
        double pivot = numbers[lo];
        while(true) {
            while(numbers[++i] < pivot) {
                if(i == hi) break;
            }
            while(pivot < numbers[--j]) {
                if(j == lo) break;
            }
            if(i >= j) break;
            double temp = numbers[i];
            numbers[i] = numbers[j];
            numbers[j] = temp;
        }
        numbers[lo] = numbers[j];
        numbers[j] = pivot;
        return j;
    }

    /**
     * Sorts an array of doubles using Merge Sort.
     * This method is static, thus it can be called as SortComparison.sort(a)
     * @param a: An unsorted array of doubles.
     * @return array sorted in ascending order
     *
     */
    /**
     * Sorts an array of doubles using iterative implementation of Merge Sort.
     * This method is static, thus it can be called as SortComparison.sort(a)
     *
     * @param a: An unsorted array of doubles.
     * @return after the method returns, the array must be in ascending sorted order.
     */

    static double[] mergeSortIterative (double a[]) {

		 //todo: implement the sort
        int n = a.length;
        double[] aux = new double[a.length];
        for (int sz  = 1; sz < n; sz = sz+sz)
        {
            for (int lo = 0; lo < n-sz; lo += sz+sz)
            {
                merge(a,aux,lo,lo+sz-1, Math.min(lo+sz+sz-1,n-1));
            }
        }
        return a;
    }//end mergesortIterative
    
    
    
    /**
     * Sorts an array of doubles using recursive implementation of Merge Sort.
     * This method is static, thus it can be called as SortComparison.sort(a)
     *
     * @param a: An unsorted array of doubles.
     * @return after the method returns, the array must be in ascending sorted order.
     */
    static double[] mergeSortRecursive (double a[]) { //Starting method which has originial array passed
    	
        double[] aux = new double[a.length];
        recursiveSort(a, aux, 0, a.length-1);
        return a;
    	//todo: implement the sort
	
    }//end mergeSortRecursive

    static void recursiveSort(double[] a,double[] aux,int lo, int hi)
    {
        if(hi<=lo) return;
        int mid = lo + (hi -lo) / 2;
        recursiveSort(a,aux,mid+1,hi);
        merge(a, aux, lo, mid, hi);
    }

    static void merge(double[] a,double[] aux,int lo, int mid, int hi)
    {
        if(a.length == 0 || aux.length == 0) return; //
        if(lo < 0) return;
        if(hi>a.length+1)return;

        for(int k = lo; k <=hi ; k++){        // Copy                                          
            aux[k] = a[k];                    // Copy                      
        }                                     // Copy
        
        int i = lo;                                                //   Merge                                                                     
        int j = mid+1;                                             //   Merge                 
        for (int k = lo; k <= hi ; k++)                            //   Merge                                 
        {                                                          //   Merge     
            if (i > mid)                    a[k] = aux[j++];       //   Merge                                                         
            else if (j > hi)                a[k] = aux[i++];       //   Merge                                                         
            else if (aux[j] < aux[i])       a[k] = aux[j++];       //   Merge                                                         
            else                            a[k] = aux[i++];       //   Merge                                                         
        }                                                          //   Merge     
    }                                                              //   Merge 
    	

    public static void main(String[] args) {

        //todo: do experiments as per assignment instructions
        System.out.println("Hello World");
    }

 }//end class

