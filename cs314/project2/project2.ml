(*
1. <E> ::= ( <W> ) AND <E> | ( <W> )
2. <W> ::= <T> OR <W> | <T>
3. <T> ::= <V> | NOT <V>
4. <V> ::= a | b | ... | z | TRUE | FALSE
*)
let validVarNames = ["a";"b";"c";"d";"e";"f";"g";"h";"i";"j";"k";"l";"m";"n";"o";"p";"q";"r";"s";"t";"u";"v";"w";"x";"y";"z"];;

(* breaks down a string list with respect to a delimiter *)
let partition (input : string list) (bound : string) : string list list =
  (* for each occurrence of delimiter, split the list. add to a result list *)
  let rec partition_helper current_sublist accumulator remaining_list = 
    match remaining_list with 
    (* prepend and reverse (but do it twice cause it's a list of lists) *)
    | [] -> List.rev (List.rev current_sublist::accumulator)
    | (hd::tl) -> 
      if hd = bound then
        (* delimiter found; add current sublist to accumulator and start a new sublist *)
        (* have to reverse because we're doing prepend approach instead of append *)
        partition_helper [] (List.rev current_sublist::accumulator) tl
      else
        (* delimiter not found, increase current sublist to include hd and leave accumulator as is *)
        partition_helper (hd::current_sublist) accumulator tl
  in partition_helper [] [] input
;;

(*
Note that one literal could be the negation of a variable or the variable
itself. The tuple string*string in the list specifies that. If a NOT is decorating a
variable, then the second item in the tuple is “NOT”; otherwise, it is an empty
string. In the above example, there are two clauses, hence, there is a list of two
lists, each list representing a clause. Each list representing a clause consists of
the components divided by the “OR” operator in the clause.
- In CNF, the clause separating operator is "AND"
- so, we could just partition by AND then by OR and finally group 'NOT'-decorated variables
*)
let buildCNF (input : string list) : (string * string) list list = 
  (* build the first AND-separated list *)
  let and_separated = partition input "AND" 
  in
  (* then, on each AND-clause, build the OR-separated list *)
  let or_separated =
    List.map (fun lst -> partition lst "OR") and_separated
  in
  (* finally, add NOT-decorations only if the variable is valid *)
  let rec not_decoration (input : string list) : (string * string) list =
    match input with 
    | [] -> []
    | "NOT" :: hd2 :: tl -> 
      if List.mem hd2 validVarNames then (hd2, "NOT") :: not_decoration tl
      else not_decoration tl
    | hd :: tl -> 
      if List.mem hd validVarNames then (hd, "") :: not_decoration tl
      else not_decoration tl
  in
  (* since or_separated is a list of list of lists, we need to flatten it *)
  List.map (fun sublist -> 
    List.flatten (List.map not_decoration sublist)
  ) or_separated
;;

let getVariables (input : string list) : string list = 
  (* for each new variable found, add to list *)
  let rec is_new_variable (current_list : string list) (new_var : string) : bool =
    match current_list with
    | [] -> true 
    | (hd::tl) -> if hd = new_var then false else is_new_variable tl new_var
  in
  let rec getVariables_helper (var_list : string list) (input_list : string list) : string list =
    match input_list with
    | [] -> List.rev var_list (* assume i need to return in order of appearance so reverse *)
    | (hd::tl) -> 
      (* first, check if hd is a valid variable name *)
      if List.mem hd validVarNames then
        (* then, check if hd is a new variable *)
        if (is_new_variable var_list hd) then getVariables_helper (hd::var_list) tl
        else getVariables_helper var_list tl
      (* else, continue *)
      else getVariables_helper var_list tl
    in getVariables_helper [] input
;;

let rec generateDefaultAssignments (varList : string list) : (string * bool) list = 
  (* for each variable found in varList, add to list as a tuple with default value of false *)
  match varList with
  | [] -> []
  (* prepend result to another recursive call of generateDefaultAssignments *)
  | (hd::tl) -> (hd, false)::generateDefaultAssignments tl
;;

let rec generateNextAssignments (assignList : (string * bool) list) : (string * bool) list * bool = 
    (*
    1. reverse list
    2. traverse list
        - if true, change to false and continue
        - if false, change to true and return
    3. if we reach end of list, this means carry is true
    4. reverse list again and return
    *)
    let rec generateNextAssignments_helper (assignList : (string * bool) list) : (string * bool) list * bool =
      match assignList with
      | [] -> ([], true)
      | (var, value)::tl -> 
        if value then
            let (newAssignList, carry) = generateNextAssignments_helper tl
            in
            ((var, false)::newAssignList, carry)
        else
            ((var, true)::tl, false)
    in
    let (newAssignList, carry) = generateNextAssignments_helper (List.rev assignList) in
    (List.rev newAssignList, carry)
;;

let rec lookupVar (assignList : (string * bool) list) (str : string) : bool = 
  (* for each tuple in assignList, check if the variable matches the string *)
  match assignList with
  | [] -> raise (Invalid_argument "Variable not found in assignment list")
  | (var, value)::tl -> if var = str then value else lookupVar tl str
;;

let evaluateCNF (t : (string * string) list list) (assignList : (string * bool) list) : bool = 
  (* each list of lists is AND-separated
   * each AND-separated list is a list of tuples, which are OR-separated
   * for each AND-separated list:
   *   maintain an accumulator to store boolean value of each OR-separated list
   *   for each OR-separated list:
   *       maintain an accumulator to store boolean value of each tuple within the list
   *       for each tuple:
   *           if tuple is NOT-decorated, then negate the value of the variable
   *           else, just use the value of the variable
   *           store boolean value of this tuple in the accumulator
   *       if any accumulator value is true, then the OR-separated list is true
   *       else, the OR-separated list is false
   *   if any OR-separated list is false, then the AND-separated list is false
   *   else, the AND-separated list is true
   *)
   let rec evaluateAnd (andList : (string * string) list list) (assignList : (string * bool) list) : bool =
    match andList with
    | [] -> true (* if we reach end of list, then the AND-separated list is true since no early return on false *)
    | (hd::tl) ->
        let rec evaluateOr (orList : (string * string) list) (assignList : (string * bool) list) : bool =
          match orList with
          | [] -> false (* if we reach end of list, then the OR-separated list is false since no early return on true *)
          | (hd::tl) ->
              let rec evaluateTuple (tuple : string * string) (assignList : (string * bool) list) : bool =
                match tuple with
                | (var, "NOT") -> not (lookupVar assignList var)
                | (var, "") -> lookupVar assignList var
                | _ -> raise (Invalid_argument "Invalid tuple format") (* should never happen *)
              in
              let tupleValue = evaluateTuple hd assignList in
              if tupleValue then true
              else evaluateOr tl assignList
        in
        let orValue = evaluateOr hd assignList in
        if orValue then evaluateAnd tl assignList
        else false
  in
  evaluateAnd t assignList
;;

let satisfy (input : string list) : (string * bool) list =
    (* return first variable assignment list that satisfied the CNF
    - try all false assignments first (default values)
    - use getNextAssignments for next assignment
    - terminate when all assignments exhausted or cnf satisfied
    *)
    let varList = getVariables input in 
    let defaultAssignments = generateDefaultAssignments varList in
    let rec tryAssignment (assignList : (string * bool) list) : (string * bool) list =
      if evaluateCNF (buildCNF input) assignList then assignList
      else
        let (newAssignList, carry) = generateNextAssignments assignList in
        if carry then [("error"), true]
        else tryAssignment newAssignList
    in
    tryAssignment defaultAssignments
;;
