#int a,b;
#void math() {
# a = ((((b + 1) + 4) / 2) & b) << 2;
#}

# b = 0x1, + 1 = 0x2, +4 = 0x6, /2 = 0x3 -> 0b0011 & 0b0001 = 0b0001, << 2 -> 0b0010


.pos 0x100
                 ld   $a, r0              # r0 = address of a
                 ld   $b, r1              # r1 = address of b
                 ld   0x0(r0), r2         # r2 = value of a
                 ld   0x0(r1), r3         # r3 = value of b, will store the modified value
                 ld  0x0(r1), r5          # r5 = original value of b 

                 inc r3                   # b + 1

                 inca r3                  # (b + 1) + 4

                 shr $0x1, r3             # ((b + 1) + 4)/2


                 and r5, r3               # (((b + 1) + 4)/2) & b
                 shl $0x2, r3             # ((((b + 1) + 4)/2) & b) << 2

                 st r3, 0x0(r0)           # a = the value in r3




                 halt                     # halt


.pos 0x1000
a:               .long 0xffffffff         # a 
.pos 0x2000
b:               .long 0x00000001         # b