(* 
QUESTION 1
------------
Write an OCaml function maxAbsoluteVal : int list → int that takes an integer list l
and returns the maximum absolute value of l.

Examples:
# maxAbsoluteVal [ ] ;;
- : int = 0
# maxAbsoluteVal [1;2;3;-4];;
- : int = 4
*)
let maxAbsoluteVal (lst: int list) : int =
    let rec foldLeft f a l =
        match l with
        | [] -> a
        | (h::t) -> foldLeft f (f a h) t
    in
    foldLeft (fun max_val x -> max (abs x) max_val) 0 lst

(*
QUESTION 2
------------
Write an OCaml function getnthdigit : int → int that gets the n-th digit
of an integer.

Examples:
# getnthdigit 12345 1;;
- : int = 1
# getnthdigit 31145 5;;
- : int = 5
*)
let getnthdigit (num: int) (n: int) : int =
    (* formula (right-to-left): (a / 10^{b-1}) mod 10
    since we want digits from left to right, we can't use b-1
    so we use numDigits - n to get correct spot. to accept negative
    numbers, need to absolut evalue the number *)
    let abs_num = abs num
    in
    let numDigits (num: int) =
      if num = 0 then 1 else int_of_float (log10 (float_of_int num) +. 1.0)
    in
    let num_digits = numDigits abs_num
    in
    if n > num_digits || n <= 0 then
      failwith "Index out of bounds"
    else
      let divisor = int_of_float (10. ** float_of_int (num_digits - n)) in
      (abs_num / divisor) mod 10

let () =
  let test_cases = [
    (12345, 1, 1);
    (12345, 2, 2);
    (12345, 3, 3);
    (12345, 4, 4);
    (12345, 5, 5);
    (31145, 1, 3);
    (31145, 2, 1);
    (31145, 3, 1);
    (31145, 4, 4);
    (31145, 5, 5);
    (987654321, 9, 1);
    (-12345, 1, 1);
    (-12345, 2, 2);
    (-12345, 3, 3);
    (-12345, 4, 4);
    (-12345, 5, 5)
  ] in
  List.iter (fun (num, n, expected) ->
    let result = getnthdigit num n in
    if result = expected then
      Printf.printf "Test passed for input (%d, %d)\n" num n
    else
      Printf.printf "Test failed for input (%d, %d): expected %d but got %d\n"
        num n expected result
  ) test_cases

(*
QUESTION 3
------------
Implement prefix sum in OCaml, psum : int list → int list. The prefix
sum algorithm takes a sequence of numbers x0, x1, x2, ... as input and
returns a sequence of numbers y0, y1, y2, ... such that y0 = x0, y1 =
x0 + x1, y2 = x0 + x1 + x2 and so on.

Examples:
# psum [1; 2; 0; -7] ;;
- : int list = [1; 3; 3; -4] ;;
# psum [0; 1; 2; 3] ;;
- : int list = [0; 1; 3; 6]
# psum [ ] ;;
- : int list = [ ]
Note: You are not allowed to use OCaml’s imperative features.
*)
let psum (lst: int list) : int list =
    let rec reverse lst acc =
        match lst with
        | [] -> acc
        | (h::t) -> reverse t (h::acc)
    in
    let rec helper (lst: int list) (sum: int) (new_lst: int list) : int list =
        match lst with
        | [] -> reverse new_lst []
        | (h::t) -> helper t (sum + h) ((sum + h)::new_lst) 
        (* prepend-and-reverse = O(1) many times then O(n) just once,
           append (new_lst @ [sum + h]) = O(n) operation many times *)
    in
    helper lst 0 []