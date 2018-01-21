#int t;
#int array[2];
#void swap() {
# t = array[0];
# array[0] = array[1];
# array[1] = t;
#}




.pos 0x100
                 ld   $t, r0              # r0 = address of t
                 ld   $array, r1          # r1 = address of array
                 ld   0x0(r1), r7         # value of array[0]
                 
                 st   r7, 0x0(r0)         # t = array[0]

                 ld   $0x01, r3           # r3 = 0x1
                 ld   (r1,r3,4), r4       # r4 = array[1]

                 st   0x0(r7)

                 st   r4,0x0(r0)          # array[0] = array [1]
                 

                 ld   0x0(r0), r5         # r5 = t
                 ld   (r1,r3,4), r6       # r6 = array[1]

                 st   r5,0x0(r6)          # array[1]  = t

                 halt                     # halt


.pos 0x1000
t:               .long 0xf0000001         # t 
.pos 0x2000
array:           .long 0xffffffff         # array[0]
                 .long 0x00000000         # array[1]
                