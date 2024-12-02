let rec length lst =
	match lst with
		| [] -> 0
		| _ :: xs -> 1 + (length [@tailcall]) xs