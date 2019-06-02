-- FUNCTION AND TABLE DROP

DROP TRIGGER IF EXISTS validate_sketch_update ON sketches;
DROP TRIGGER IF EXISTS validate_sketch_insert ON sketches;
DROP FUNCTION IF EXISTS validate_sketch_update;

DROP TRIGGER IF EXISTS validate_folder_update ON folders;
DROP TRIGGER IF EXISTS validate_folder_insert ON folders;
DROP FUNCTION IF EXISTS validate_folder_update;

DROP FUNCTION IF EXISTS change_folder_owner;
DROP FUNCTION IF EXISTS delete_sketch;
DROP FUNCTION IF EXISTS rename_sketch;
DROP FUNCTION IF EXISTS create_sketch;
DROP FUNCTION IF EXISTS update_sketch;
DROP FUNCTION IF EXISTS unshare_folder_with_user;
DROP FUNCTION IF EXISTS share_folder_with_user;

DROP TABLE IF EXISTS shares;
DROP TABLE IF EXISTS sketches;
DROP TABLE IF EXISTS folders;
DROP TABLE IF EXISTS users;


-- TABLES

CREATE TABLE users (
	id SERIAL PRIMARY KEY,
	name VARCHAR(100) UNIQUE,
	password VARCHAR(200),
	role NUMERIC(1)
);

CREATE TABLE folders (
	id SERIAL PRIMARY KEY,
	name VARCHAR(100),
	owner INT REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE sketches (
	id SERIAL PRIMARY KEY,
	name VARCHAR(100),
	folders_id INT REFERENCES folders(id)  ON DELETE CASCADE,
	content TEXT
);

CREATE TABLE shares (
	users_id INT REFERENCES users(id) ON DELETE CASCADE,
	folders_id INT REFERENCES folders(id)  ON DELETE CASCADE,
	PRIMARY KEY (users_id, folders_id)
);


-- INSERT / UPDATE / DELETE FUNCTIONS
/*
The following functions are used to simplify table insertions and updates where user verification
would normally call for multiple SQL queries made by the db client. Using these functions requires
only a single query, and everything else is taken care of at db level. 
In the params, 'owner' stands for the user id of the function caller needed to be verified.
In case of illegal owners, the functions throw exceptions.

*/

-- Change ownership of an existing folder
-- params: folder_id, owner_name
CREATE FUNCTION change_folder_owner(int, text) RETURNS void 
AS '
DECLARE
	new_owner_id integer;
BEGIN
	new_owner_id = (SELECT id FROM users WHERE name = $2);
	IF new_owner_id IS NULL THEN
		RAISE EXCEPTION ''Invalid user name'';
	END IF; 
	UPDATE folders SET owner = new_owner_id WHERE id = $1;
END; '
LANGUAGE plpgsql;

-- Share a folder with user.
-- params: owner, folder id, user name
CREATE FUNCTION share_folder_with_user(int, int, text) RETURNS void 
AS '
DECLARE	
	user_id integer;
BEGIN
	user_id = (SELECT id FROM users WHERE name = $3);
	IF user_id IS NULL THEN
		RAISE EXCEPTION ''User doesn''''t exist '';
	END IF;
	IF (SELECT owner FROM folders WHERE id = $2) != $1 THEN
		RAISE EXCEPTION ''Folders sharing can only be managed by owners'';
	END IF;
	INSERT INTO shares (users_id, folders_id) VALUES (user_id, $2);
END; '
LANGUAGE plpgsql;

-- Unshare an existing shared folder with a user
-- params: owner, folder id, user id
CREATE FUNCTION unshare_folder_with_user(int, int, int) RETURNS void 
AS '
BEGIN 
	IF (SELECT owner FROM folders WHERE id = $2) != $1 THEN
		RAISE EXCEPTION ''Folders sharing can only be managed by owners'';
	END IF;
	DELETE FROM shares WHERE users_id = $3 AND folders_id = $2;
END; '
LANGUAGE plpgsql;

-- Change every data of a sketch. Should be used when saving an edited sketch.
-- params: 1 owner, 2 id, 3 folderId, 4 name, 5 content
CREATE FUNCTION update_sketch(int, int, int, text, text) RETURNS void
AS ' 
BEGIN
	IF (SELECT users.id FROM users LEFT JOIN folders ON users.id = owner
		WHERE folders.id = $3) != $1 THEN
		RAISE EXCEPTION ''Sketches can only be updated by the owners of their folders'';
	END IF;
	UPDATE sketches SET folders_id = $3, name = $4, content = $5 WHERE id = $2;
END;'
LANGUAGE plpgsql;

-- Create a new empty sketch without content.
-- params: owner, folder_id, name
CREATE FUNCTION create_sketch(int, int, text) RETURNS void
AS 
'BEGIN
	IF (SELECT owner FROM folders WHERE id = $2) != $1 THEN
		RAISE EXCEPTION ''Sketches can only be created by the owners of their folders'';
	END IF;
	INSERT INTO sketches (folders_id, name) VALUES ($2, $3);
END;'
LANGUAGE plpgsql;

-- Rename existing sketch
-- params: owner, sketch_id, name
CREATE FUNCTION rename_sketch(int, int, text) RETURNS void
AS 
'BEGIN
	IF (SELECT owner FROM folders
		RIGHT JOIN sketches ON folders.id = folders_id
		WHERE sketches.id = $2) != $1 THEN
		RAISE EXCEPTION ''Sketches can only be renamed by the owners of their folders'';
	END IF;
	UPDATE sketches SET name = $3 WHERE id = $2;
END;'
LANGUAGE plpgsql;

-- Delete an existing sketch
-- params: owner, sketch_id
CREATE FUNCTION delete_sketch(int, int) RETURNS void
AS ' 
BEGIN
	IF (SELECT owner FROM folders
		RIGHT JOIN sketches ON folders.id = folders_id
		WHERE sketches.id = $2) != $1 THEN
		RAISE EXCEPTION ''Sketches can only be deleted by the owners of their folders'';
	END IF;
	DELETE FROM sketches WHERE id = $2;
END;'
LANGUAGE plpgsql;


-- TRIGGER FUNCTIONS

-- Check name uniqueness among folders of the same owner
CREATE FUNCTION validate_folder_update() RETURNS trigger
AS '
BEGIN
	IF (SELECT id FROM folders 
		WHERE name = NEW.name AND owner = NEW.owner LIMIT 1) IS NOT NULL THEN
		RAISE EXCEPTION ''Folder name must be unique!'';
	END IF;
	RETURN NEW;
END;'
LANGUAGE plpgsql;
CREATE TRIGGER validate_folder_insert BEFORE INSERT ON folders
FOR EACH ROW EXECUTE PROCEDURE validate_folder_update();
CREATE TRIGGER validate_folder_update BEFORE UPDATE ON folders
FOR EACH ROW EXECUTE PROCEDURE validate_folder_update();

-- Check name uniqueness among sketches in the same folder
CREATE FUNCTION validate_sketch_update() RETURNS trigger
AS '
BEGIN
	IF (SELECT id FROM sketches 
		WHERE name = NEW.name AND folders_id = NEW.folders_id LIMIT 1) IS NOT NULL THEN
		RAISE EXCEPTION ''Sketch name must be unique'';
	END IF;
	RETURN NEW;
END;'
LANGUAGE plpgsql;
CREATE TRIGGER validate_sketch_insert BEFORE INSERT ON sketches
FOR EACH ROW EXECUTE PROCEDURE validate_sketch_update();
CREATE TRIGGER validate_sketch_update BEFORE UPDATE ON sketches
FOR EACH ROW EXECUTE PROCEDURE validate_sketch_update();


-- STOCK DATA INSERTION

INSERT INTO users (name, password, role) VALUES 
('a', 'a', '1'), --1
('b', 'a', '0'), --2
('Joe', 'a', '0'), --3
('Rose', 'a', '0'), --4
('Vladimir', 'a', '0') --5
;


INSERT INTO folders (name, owner) VALUES
('Work', 1), --1
('Unfinished', 1), --2
('B''s Sketches', 2) --3
;

INSERT INTO sketches (name, folders_id, content) VALUES 
('Animal study', 1, '{"lines":[[[361,387],[419,499],"black"],[[420,501],[468,541],"black"],[[465,540],[506,518],"black"],[[506,518],[531,383],"black"],[[531,383],[505,332],"black"],[[505,332],[448,315],"black"],[[448,315],[396,324],"black"],[[397,324],[363,389],"black"],[[394,334],[360,277],"black"],[[328,259],[360,279],"black"],[[328,260],[289,255],"black"],[[288,259],[324,273],"black"],[[324,277],[352,302],"black"],[[355,305],[383,352],"black"],[[487,328],[505,290],"black"],[[506,290],[535,258],"black"],[[535,258],[587,242],"black"],[[587,242],[563,265],"black"],[[531,285],[521,303],"black"],[[523,305],[507,338],"black"],[[532,290],[567,265],"black"],[[370,382],[259,352],"black"],[[259,352],[232,372],"black"],[[233,374],[261,405],"black"],[[262,405],[369,402],"black"],[[519,371],[611,302],"black"],[[611,302],[640,315],"black"],[[640,315],[642,349],"black"],[[642,349],[532,388],"black"],[[384,406],[403,399],"black"],[[403,399],[413,411],"black"],[[413,411],[411,411],"black"],[[387,408],[394,418],"black"],[[394,419],[414,411],"black"],[[486,400],[503,395],"black"],[[503,395],[514,404],"black"],[[514,404],[505,413],"black"],[[505,413],[490,401],"black"],[[443,507],[462,516],"black"],[[462,516],[491,506],"black"],[[442,477],[459,467],"black"],[[460,468],[480,470],"black"],[[480,470],[486,475],"black"],[[487,477],[482,491],"black"],[[482,491],[464,492],"black"],[[463,492],[443,480],"black"],[[451,479],[466,485],"black"],[[466,485],[481,482],"black"],[[466,487],[464,493],"black"],[[523,462],[716,475],"black"],[[716,475],[792,397],"black"],[[792,397],[905,392],"black"],[[905,392],[981,393],"black"],[[981,393],[1035,427],"black"],[[1036,427],[1065,468],"black"],[[1065,468],[1075,521],"black"],[[1075,521],[1017,556],"black"],[[1017,556],[943,579],"black"],[[944,580],[824,601],"black"],[[824,601],[768,589],"black"],[[765,588],[738,564],"black"],[[737,563],[722,536],"black"],[[722,536],[718,506],"black"],[[718,504],[718,480],"black"],[[718,480],[764,587],"black"],[[763,591],[624,723],"black"],[[766,593],[841,733],"black"],[[1004,567],[976,720],"black"],[[1009,564],[1142,708],"black"],[[624,722],[595,750],"black"],[[595,750],[636,759],"black"],[[636,759],[630,724],"black"],[[629,724],[619,753],"black"],[[839,733],[808,761],"black"],[[809,761],[843,762],"black"],[[843,762],[842,734],"black"],[[841,734],[826,761],"black"],[[977,721],[947,754],"black"],[[947,754],[992,754],"black"],[[992,754],[977,725],"black"],[[977,725],[971,756],"black"],[[1142,712],[1126,742],"black"],[[1126,742],[1161,742],"black"],[[1161,742],[1145,711],"black"],[[1144,714],[1146,744],"black"]]}'
), -- 1
('Flowers', 1, '{"lines":[[[772,482],[674,652],"black"],[[674,652],[776,842],"black"],[[777,843],[1019,861],"black"],[[1019,861],[1159,735],"black"],[[1159,734],[1079,538],"black"],[[781,496],[847,571],"black"],[[847,571],[939,584],"black"],[[939,584],[1084,547],"black"],[[1084,547],[1034,465],"black"],[[1034,465],[876,441],"black"],[[876,441],[784,497],"black"],[[844,530],[720,161],"black"],[[922,503],[962,255],"black"],[[1027,522],[1221,248],"black"],[[729,176],[645,95],"black"],[[645,95],[728,59],"black"],[[728,59],[787,152],"black"],[[795,163],[737,178],"black"],[[972,266],[893,185],"black"],[[893,185],[1075,66],"black"],[[1075,66],[1144,201],"black"],[[1144,201],[973,265],"black"],[[1220,269],[1149,47],"black"],[[1153,49],[1469,137],"black"],[[1469,137],[1216,267],"black"]]}'
), -- 2

('Jenny Tries Symmetry', 2, '{"lines":[[[562,800],[872,369],"black"],[[876,375],[1187,797],"black"],[[1187,797],[569,810],"black"],[[755,816],[742,930],"black"],[[1014,815],[1014,911],"black"],[[881,377],[859,262],"black"],[[866,272],[755,249],"black"],[[755,249],[697,170],"black"],[[696,170],[719,65],"black"],[[719,65],[840,24],"black"],[[845,24],[1041,30],"black"],[[1041,30],[1086,88],"black"],[[1091,99],[1134,164],"black"],[[1135,169],[1106,214],"black"],[[1078,235],[977,301],"black"],[[977,301],[880,279],"black"],[[1086,246],[1114,226],"black"],[[831,81],[795,97],"black"],[[795,97],[829,134],"black"],[[829,134],[877,129],"black"],[[877,129],[849,89],"black"],[[1002,94],[962,120],"black"],[[963,126],[991,165],"black"],[[991,165],[1027,140],"black"],[[1027,140],[1008,97],"black"],[[795,166],[835,210],"black"],[[835,210],[893,227],"black"],[[893,227],[970,231],"black"],[[970,231],[1011,218],"black"],[[1011,218],[1030,231],"black"],[[1040,237],[1048,252],"black"],[[1072,52],[1230,211],"black"],[[1230,211],[1317,428],"black"],[[742,69],[632,159],"black"],[[632,159],[498,349],"black"],[[707,161],[651,278],"black"],[[637,314],[609,352],"black"],[[1140,200],[1223,379],"black"],[[1223,379],[1311,562],"black"],[[1095,249],[1130,349],"black"],[[1142,344],[1220,503],"black"],[[774,262],[738,329],"black"],[[738,329],[677,457],"black"],[[1009,301],[1016,327],"black"],[[1017,328],[1136,544],"black"],[[1042,597],[1649,224],"black"],[[706,610],[332,230],"black"],[[1565,187],[1524,101],"black"],[[1524,101],[1559,57],"black"],[[1559,57],[1643,55],"black"],[[1643,55],[1706,102],"black"],[[1706,102],[1740,205],"black"],[[1740,206],[1620,223],"black"],[[1620,223],[1568,193],"black"],[[1528,108],[1425,25],"black"],[[1425,25],[1561,69],"black"],[[1641,62],[1730,19],"black"],[[1730,19],[1711,105],"black"],[[1576,118],[1629,134],"black"],[[1678,108],[1664,144],"black"],[[1651,163],[1669,200],"black"],[[1685,224],[1704,606],"black"],[[1709,615],[1552,771],"black"],[[1717,616],[1835,776],"black"],[[1706,430],[1619,369],"black"],[[1715,419],[1831,319],"black"]]}'
) -- 3
;

INSERT INTO shares (folders_id, users_id) VALUES 
(1, 2);



